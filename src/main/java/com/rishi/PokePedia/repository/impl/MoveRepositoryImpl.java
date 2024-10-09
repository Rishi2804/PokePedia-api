package com.rishi.PokePedia.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.model.enums.MoveClass;
import com.rishi.PokePedia.model.enums.Type;
import com.rishi.PokePedia.model.enums.VersionGroup;
import com.rishi.PokePedia.repository.MoveRepository;
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
public class MoveRepositoryImpl implements MoveRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public MoveRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public Optional<Move> getMoveById(Integer id) {
        String sql = "SELECT * FROM move WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, MoveRepositoryImpl::moveRowMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Move> getMoveByName(String name) {
        String sql = "SELECT * FROM move WHERE name = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, MoveRepositoryImpl::moveRowMapper, name));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PokemonSnap> getPokemonLearnable(Integer id) {
        String sql = "SELECT DISTINCT p.id, p.name, p.type1, p.type2, p.species_id FROM movedetails " +
                "JOIN pokemon p ON p.id = pokemon_id " +
                "WHERE move_id = ? " +
                "ORDER BY p.species_id, p.id ASC";
        return jdbcTemplate.query(sql, MoveRepositoryImpl::pokemonLearnableMoveMapper, id);
    }

    @Override
    public List<PastMoveValues> getPastValues(Integer id) {
        String sql = "SELECT * FROM pastmovevalues WHERE id = ?";
        return jdbcTemplate.query(sql, MoveRepositoryImpl::pastMoveValueMapper, id);
    }


    private static Move moveRowMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String[] descriptionsJsonStrs = (String[]) rs.getArray("descriptions").getArray();
        List<Move.Description> descriptions = new ArrayList<>();
        record DescriptionExtractor (String entry, String[] versionGroup){ }
        for (String descriptionsJsonStr : descriptionsJsonStrs) {
            try {
                DescriptionExtractor temp = objectMapper.readValue(descriptionsJsonStr, DescriptionExtractor.class);
                descriptions.add(new Move.Description(
                        temp.entry(),
                        Arrays.stream(temp.versionGroup())
                                .map(val -> VersionGroup.fromName(val)).toArray(VersionGroup[]::new))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new Move(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("gen"),
                MoveClass.fromString(rs.getString("class")),
                rs.getInt("power"),
                rs.getInt("accuracy"),
                rs.getInt("pp"),
                descriptions
        );
    }

    private static PokemonSnap pokemonLearnableMoveMapper(ResultSet rs, Integer rowNum) throws SQLException {
        String type2Str = rs.getString("type2");
        Type type2 = type2Str == null ? null : Type.fromString(type2Str);
        return new PokemonSnap(
                null,
                rs.getInt("id"),
                rs.getString("name"),
                Type.fromString(rs.getString("type1")),
                type2
        );
    }

    private static PastMoveValues pastMoveValueMapper(ResultSet rs, Integer rowNum) throws SQLException {
        return new PastMoveValues(
                rs.getInt("power"),
                rs.getInt("accuracy"),
                rs.getInt("pp"),
                Arrays.stream(((String[]) rs.getArray("version_groups").getArray()))
                        .map(group -> VersionGroup.fromName(group)).toArray(VersionGroup[]::new)
        );
    }
}
