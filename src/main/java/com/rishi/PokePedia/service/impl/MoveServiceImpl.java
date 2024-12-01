package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.dto.MoveSnapDto;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.model.enums.LearnMethod;
import com.rishi.PokePedia.model.enums.VersionGroup;
import com.rishi.PokePedia.repository.MoveRepository;
import com.rishi.PokePedia.service.MoveService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rishi.PokePedia.service.utils.formatName;

@Service
public class MoveServiceImpl implements MoveService {
    private final MoveRepository moveRepository;

    public MoveServiceImpl(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public List<MoveSnapDto> getMoves() {
        List<MoveSnap> moves = moveRepository.getMoves();
        return mapToMoveSnapDto(moves);
    }

    @Override
    public Optional<MoveDto> getMoveById(Integer id) {
        Optional<Move> move = moveRepository.getMoveById(id);
        return move.map(val -> {
            List<MovePokemonLearnable> pokemon = moveRepository.getPokemonLearnable(id);
            List<PastMoveValues> pastValues = moveRepository.getPastValues(id);
            return mapToMoveDto(val, pokemon, pastValues);
        });
    }

    @Override
    public Optional<MoveDto> getMoveByName(String name) {
        Optional<Move> move = moveRepository.getMoveByName(name);
        return move.map(val -> {
            List<MovePokemonLearnable> pokemon = moveRepository.getPokemonLearnable(val.id());
            List<PastMoveValues> pastValues = moveRepository.getPastValues(val.id());
            return mapToMoveDto(val, pokemon, pastValues);
        });
    }

    private List<MoveSnapDto> mapToMoveSnapDto(List<MoveSnap> moves) {
        return moves.stream().map(move -> new MoveSnapDto(
                move.id(),
                formatName(move.name(), false),
                move.type().name(),
                move.moveClass().name(),
                move.power(),
                move.accuracy(),
                move.pp(),
                move.gen()
        )).toList();
    }

    private MoveDto mapToMoveDto(Move move, List<MovePokemonLearnable> pokemon, List<PastMoveValues> pastMoveValues) {

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
                move.effect(),
                move.descriptions().stream().map(val -> new MoveDto.Description(
                        Arrays.stream(val.versionGroups()).map(group -> group.name()).toArray(String[]::new),
                        val.entry()
                )).toList(),
                moveLearnableHelper(pokemon)
        );
    }

    private List<MoveDto.PokemonLearnable> moveLearnableHelper(List<MovePokemonLearnable> items) {
        Map<LearnMethod, List<MovePokemonLearnable>> groupedByMethod = items.stream()
                .collect(Collectors.groupingBy(MovePokemonLearnable::method));

        return groupedByMethod.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(LearnMethod.ORDER))
                .map(entry -> {
                    LearnMethod method = entry.getKey();
                    List<MovePokemonLearnable> pokemonVals = entry.getValue();
                    List<MoveDto.PokemonLearnable.Pokemon> pokemon = pokemonVals.stream().map(mon -> new MoveDto.PokemonLearnable.Pokemon(
                            mon.speciedId(),
                            mon.pokemonId(),
                            mon.name(),
                            mon.type1().name(),
                            mon.type2() == null ? null : mon.type2().name()
                    )).toList();
                    return new MoveDto.PokemonLearnable(method.name(), pokemon);
                } ).toList();
    }
}
