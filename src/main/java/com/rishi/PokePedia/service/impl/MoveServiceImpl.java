package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.model.Move;
import com.rishi.PokePedia.model.PokemonDexSnap;
import com.rishi.PokePedia.repository.MoveRepository;
import com.rishi.PokePedia.service.MoveService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            List<PokemonDexSnap> pokemon = moveRepository.getPokemonLearnable(id);
            return mapToMoveDto(val, pokemon);
        });
    }

    @Override
    public Optional<MoveDto> getMoveByName(String name) {
        Optional<Move> move = moveRepository.getMoveByName(name);
        return move.map(val -> {
            List<PokemonDexSnap> pokemon = moveRepository.getPokemonLearnable(val.id());
            return mapToMoveDto(val, pokemon);
        });
    }

    private MoveDto mapToMoveDto(Move move, List<PokemonDexSnap> pokemon) {
        return new MoveDto(
                move.id(),
                move.name(),
                move.moveClass().toString(),
                move.movePower(),
                move.moveAccuracy(),
                move.movePP(),
                move.descriptions().stream().map(val -> new MoveDto.Description(
                        Arrays.stream(val.versionGroups()).map(group -> group.name()).toArray(String[]::new),
                        val.entry()
                )).toList(),
                pokemon.stream().map(mon -> new MoveDto.Pokemon(
                        mon.id(), mon.name(), mon.type1().name(), mon.type2() == null ? null : mon.type2().name()
                )).toList()
        );
    }
}
