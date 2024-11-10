package com.rishi.PokePedia.repository.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.model.enums.*;
import com.rishi.PokePedia.repository.PokemonRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class PokemonRepositoryImpl implements PokemonRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public PokemonRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Pokemon> getPokemonById(Integer id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, PokemonRepositoryImpl::pokemonRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Pokemon> getPokemonByName(String name) {
        String sql = "SELECT * FROM pokemon WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, PokemonRepositoryImpl::pokemonRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DexNumbers> getDexNumbersFromPokemon(Integer id) {
        String sql = "SELECT * FROM dexnumber WHERE default_variate = ? OR alt_variates @> ARRAY[?]";
        return jdbcTemplate.query(sql, PokemonRepositoryImpl::dexNumberRowMapper, id, id);
    }

    @Override
    public List<EvolutionLine> getEvolutionChainOfPokemon(Integer id) {
        String sql = "SELECT * FROM get_evolution_chain_by_id(?)";
        return jdbcTemplate.query(sql, PokemonRepositoryImpl::evolutionRowMapper, id);
    }

    @Override
    public List<PokemonMoveDetails> getMovesOfPokemon(Integer id) {
        String sql = "SELECT * FROM get_pokemon_moves(?)";
        return jdbcTemplate.query(sql, PokemonRepositoryImpl::pokemonMovesMapper, id);
    }

    @Override
    public List<PokemonAbility> getAbilitiesOfPokemon(Integer id) {
        String sql = "SELECT a.name, d.* FROM abilitydetails d " +
                "JOIN ability a ON a.id = d.ability_id " +
                "WHERE d.pokemon_id = ?";
        return jdbcTemplate.query(sql, PokemonRepositoryImpl::pokemonAbilityMapper, id);
    }

    @Override
    public Optional<String> getSpeciesName(Integer id) {
        String sql = "SELECT name FROM species WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) -> rs.getString("name"), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> getSpeciesId(String name) {
        String sql = "SELECT id FROM species WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (ResultSet rs, int rowNum) -> rs.getInt("id"), name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Integer> getPokemonIdsFromSpeciesId(Integer id) {
        String sql = "SELECT id FROM pokemon WHERE species_id = ? ORDER BY id ASC";
        return jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> rs.getInt("id"), id);
    }

    @Override
    public List<PokemonSnap> getDexByRegion(PokedexRegion pokedexRegion) {
        if (pokedexRegion == PokedexRegion.NATIONAL) {
            String sql = "SELECT s.id AS num, p.id, s.name, p.type1, p.type2 FROM pokemon p " +
                    "JOIN species s ON s.id = p.species_id " +
                    "ORDER BY s.id, p.id ASC";
            return jdbcTemplate.query(sql, PokemonRepositoryImpl::pokedexRowMapper);
        } else {
            String sql = "SELECT d.num, p.id, s.name, " +
                    "COALESCE(pt.type1, p.type1) AS type1, " +
                    "CASE WHEN pt.type1 IS NOT NULL THEN pt.type2 ELSE p.type2 END AS type2 " +
                    "FROM dexnumber d " +
                    "JOIN pokemon p ON d.default_variate = p.id OR d.alt_variates @> ARRAY[p.id] " +
                    "JOIN species s ON s.id = d.species_id " +
                    "LEFT JOIN pasttypes pt ON p.id = pt.pokemon_id AND pt.gen >= ? " +
                    "WHERE d.name::text = ? " +
                    "ORDER BY d.num, CASE WHEN p.id = d.default_variate THEN 0 ELSE 1 END";
            return jdbcTemplate.query(sql, PokemonRepositoryImpl::pokedexRowMapper, pokedexRegion.getGen(), pokedexRegion.getName());
        }
    }

    private static Pokemon pokemonRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);

        Array formsArray = rs.getArray("forms");
        String[] forms = formsArray == null ? null : (String[]) formsArray.getArray();

        String[] dexEntriesJsonStrs = (String[]) rs.getArray("dex_entries").getArray();
        Pokemon.DexEntry[] dexEntries = new Pokemon.DexEntry[dexEntriesJsonStrs.length];
        record DexEntryExtractor (String game, String entry){}
        for (int i = 0; i < dexEntriesJsonStrs.length; i++) {
            try {
                DexEntryExtractor extract = objectMapper.readValue(dexEntriesJsonStrs[i], DexEntryExtractor.class);
                dexEntries[i] = new Pokemon.DexEntry(Game.fromName(extract.game()), extract.entry());
            } catch (Exception e) {
                e.printStackTrace();
                dexEntries[i] = null;
            }
        }

        Arrays.sort(dexEntries, Comparator.comparing(Pokemon.DexEntry::game, Game.ORDER));

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

    private static PokemonSnap pokedexRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);
        return new PokemonSnap(
                rs.getInt("num"),
                rs.getInt("id"),
                rs.getString("name"),
                Type.fromString(rs.getString("type1")),
                type2
        );
    }

    private static EvolutionLine evolutionRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        return new EvolutionLine(
                rs.getInt("id"),
                rs.getInt("from_pokemon"),
                rs.getString("from_display"),
                rs.getInt("to_pokemon"),
                rs.getString("to_display"),
                (String[]) rs.getArray("details").getArray(),
                rs.getString("region"),
                rs.getInt("alt_form")
        );
    }

    private static PokemonMoveDetails pokemonMovesMapper(ResultSet rs, Integer rowNum) throws SQLException {
        Integer power = rs.getInt("power");
        power = rs.wasNull() ? null : power;

        Integer accuracy = rs.getInt("accuracy");
        accuracy = rs.wasNull() ? null : accuracy;

        Integer pp = rs.getInt("pp");
        pp = rs.wasNull() ? null : pp;

        return new PokemonMoveDetails(
                rs.getInt("move_id"),
                rs.getString("name"),
                Type.fromString(rs.getString("type")),
                MoveClass.fromString(rs.getString("class")),
                power,
                accuracy,
                pp,
                LearnMethod.fromName(rs.getString("method")),
                rs.getInt("level_learned"),
                VersionGroup.fromName(rs.getString("version"))
        );
    }

    private static PokemonAbility pokemonAbilityMapper(ResultSet rs, Integer rowNum) throws SQLException {
        Integer gen = rs.getInt("gen");
        gen = rs.wasNull() ? null : gen;

        return new PokemonAbility(
                rs.getInt("ability_id"),
                rs.getString("name"),
                rs.getBoolean("hidden"),
                gen
        );
    }
}