package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.model.Move;
import com.rishi.PokePedia.model.PastMoveValues;
import com.rishi.PokePedia.model.PokemonSnap;
import com.rishi.PokePedia.repository.MoveRepository;
import com.rishi.PokePedia.service.MoveService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.rishi.PokePedia.service.utils.formatName;

@Service
public class MoveServiceImpl implements MoveService {
    private final MoveRepository moveRepository;

    public MoveServiceImpl(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public Optional<MoveDto> getMoveById(Integer id) {
        Optional<Move> move = moveRepository.getMoveById(id);
        return move.map(val -> {
            List<PokemonSnap> pokemon = moveRepository.getPokemonLearnable(id);
            List<PastMoveValues> pastValues = moveRepository.getPastValues(id);
            return mapToMoveDto(val, pokemon, pastValues);
        });
    }

    @Override
    public Optional<MoveDto> getMoveByName(String name) {
        Optional<Move> move = moveRepository.getMoveByName(name);
        return move.map(val -> {
            List<PokemonSnap> pokemon = moveRepository.getPokemonLearnable(val.id());
            List<PastMoveValues> pastValues = moveRepository.getPastValues(val.id());
            return mapToMoveDto(val, pokemon, pastValues);
        });
    }

    private MoveDto mapToMoveDto(Move move, List<PokemonSnap> pokemon, List<PastMoveValues> pastMoveValues) {
        return new MoveDto(
                move.id(),
                formatName(move.name(), false),
                move.type().name(),
                move.gen(),
                move.moveClass().toString(),
                move.movePower(),
                move.moveAccuracy(),
                move.movePP(),
                pastMoveValues.stream().map(pastVal -> new MoveDto.PastMoveValues(
                        pastVal.movePower(),
                        pastVal.moveAccuracy(),
                        pastVal.movePP(),
                        Arrays.stream(pastVal.versionGroups()).map(group -> group.name()).toArray(String[]::new)
                )).toList(),
                move.descriptions().stream().map(val -> new MoveDto.Description(
                        Arrays.stream(val.versionGroups()).map(group -> group.name()).toArray(String[]::new),
                        val.entry()
                )).toList(),
                pokemon.stream().map(mon -> new MoveDto.Pokemon(
                        mon.id(), formatName(mon.name(), true), mon.type1().name(), mon.type2() == null ? null : mon.type2().name()
                )).toList()
        );
    }
}
