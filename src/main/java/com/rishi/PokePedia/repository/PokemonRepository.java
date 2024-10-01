package com.rishi.PokePedia.repository;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishi.PokePedia.model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class PokemonRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public PokemonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Pokemon getPokemonById(Integer id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, PokemonRepository::pokemonRowMapper, id);
    }

    public List<DexNumbers> getDexNumbersFromPokemon(Integer id) {
        String sql = "SELECT * FROM dexnumber WHERE default_variate = ? OR alt_variates @> ARRAY[?]";
        return jdbcTemplate.query(sql, PokemonRepository::dexNumberRowMapper, id, id);
    }

    public List<PokemonDexSnap> getDexByRegion(PokedexRegion pokedexRegion) {
        String sql = "SELECT d.num, p.id, s.name, " +
                    "COALESCE(pt.type1, p.type1) AS type1, " +
                    "CASE WHEN pt.type1 IS NOT NULL THEN pt.type2 ELSE p.type2 END AS type2 " +
                    "FROM dexnumber d " +
                    "JOIN pokemon p ON d.default_variate = p.id OR d.alt_variates @> ARRAY[p.id] " +
                    "JOIN species s ON s.id = d.species_id " +
                    "LEFT JOIN pasttypes pt ON p.id = pt.pokemon_id AND pt.gen >= ? " +
                    "WHERE d.name::text = ? " +
                    "ORDER BY d.num, CASE WHEN p.id = d.default_variate THEN 0 ELSE 1 END";
        return jdbcTemplate.query(sql, PokemonRepository::pokedexRowMapper, pokedexRegion.getGen(), pokedexRegion.getName());
    }

    private static Pokemon pokemonRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);

        Array formsArray = rs.getArray("forms");
        String[] forms = formsArray == null ? null : (String[]) formsArray.getArray();

        String[] dexEntriesJsonStrs = (String[]) rs.getArray("dex_entries").getArray();
        Pokemon.DexEntry[] dexEntries = new Pokemon.DexEntry[dexEntriesJsonStrs.length];
        for (int i = 0; i < dexEntriesJsonStrs.length; i++) {
            try {
                dexEntries[i] = objectMapper.readValue(dexEntriesJsonStrs[i], Pokemon.DexEntry.class);
            } catch (Exception e) {
                e.printStackTrace();
                dexEntries[i] = null;
            }
        }

        return new Pokemon(
                rs.getInt("id"),
                rs.getInt("species_id"),
                rs.getString("name"),
                rs.getInt("gen"),
                Type.fromString(rs.getString("type1")),
                type2,
                rs.getFloat("weight"),
                rs.getFloat("height"),
                rs.getInt("gender_rate"),
                rs.getInt("hp"),
                rs.getInt("atk"),
                rs.getInt("def"),
                rs.getInt("spatk"),
                rs.getInt("spdef"),
                rs.getInt("speed"),
                rs.getInt("bst"),
                forms,
                dexEntries
        );
    }

    private static DexNumbers dexNumberRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        return new DexNumbers(
                rs.getInt("num"),
                PokedexRegion.fromName(rs.getString("name"))
        );
    }

    private static PokemonDexSnap pokedexRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);
        return new PokemonDexSnap(
                rs.getInt("num"),
                rs.getInt("id"),
                rs.getString("name"),
                Type.fromString(rs.getString("type1")),
                type2
        );
    }
}