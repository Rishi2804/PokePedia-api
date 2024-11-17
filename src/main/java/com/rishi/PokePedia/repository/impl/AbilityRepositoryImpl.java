package com.rishi.PokePedia.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishi.PokePedia.model.Ability;
import com.rishi.PokePedia.model.PokemonSnap;
import com.rishi.PokePedia.model.enums.Type;
import com.rishi.PokePedia.model.enums.VersionGroup;
import com.rishi.PokePedia.repository.AbilityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class AbilityRepositoryImpl implements AbilityRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AbilityRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Optional<Ability> getAbilityById(Integer id) {
        String sql = "SELECT * FROM ability WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, AbilityRepositoryImpl::abilityRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Ability> getAbilityByName(String name) {
        String sql = "SELECT * FROM ability WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, AbilityRepositoryImpl::abilityRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PokemonSnap> getPokemonLearnable(Integer id) {
        String sql = "SELECT DISTINCT p.id, p.name, p.type1, p.type2, p.species_id FROM abilitydetails " +
                "JOIN pokemon p ON p.id = pokemon_id " +
                "WHERE ability_id = ? " +
                "ORDER BY p.species_id, p.id ASC";
        return jdbcTemplate.query(sql, AbilityRepositoryImpl::pokemonLearnableMoveMapper, id);
    }

    private static Ability abilityRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String[] descriptionsJsonStrs = (String[]) rs.getArray("descriptions").getArray();
        List<Ability.Description> descriptions = new ArrayList<>();
        record DescriptionExtractor (String entry, String[] versionGroup){ }
        for (String descriptionsJsonStr : descriptionsJsonStrs) {
            try {
                DescriptionExtractor temp = objectMapper.readValue(descriptionsJsonStr, DescriptionExtractor.class);
                descriptions.add(new Ability.Description(
                        temp.entry(),
                        Arrays.stream(temp.versionGroup())
                                .map(val -> VersionGroup.fromName(val)).toArray(VersionGroup[]::new))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Ability(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("gen"),
                descriptions
        );
    }

    private static PokemonSnap pokemonLearnableMoveMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);
        return new PokemonSnap(
                null,
                rs.getInt("species_id"),
                rs.getInt("id"),
                rs.getString("name"),
                Type.fromString(rs.getString("type1")),
                type2
        );
    }
}
