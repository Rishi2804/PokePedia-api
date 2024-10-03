package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.PokemonDexSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.repository.PokemonRepository;
import com.rishi.PokePedia.service.PokemonService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public Optional<PokemonDto> getPokemonById(Integer id) {
        Optional<Pokemon> pokemon = pokemonRepository.getPokemonById(id);
        List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
        List<EvolutionLine> evolutionChain = pokemonRepository.getEvolutionChainOfPokemon(id);
        List<PokemonMoveDetails> pokemonMoves = pokemonRepository.getMovesOfPokemon(id);
        List<PokemonAbility> pokemonAbilities = pokemonRepository.getAbilitiesOfPokemon(id);
        return pokemon.map(value -> mapToPokemonDto(value, dexNumbers, evolutionChain, pokemonMoves, pokemonAbilities));
    }

    @Override
    public Optional<PokemonDto> getPokemonByName(String name) {
        Optional<Pokemon> pokemon = pokemonRepository.getPokemonByName(name);
        return pokemon.map(value -> {
            Integer id = value.id();
            List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
            List<EvolutionLine> evolutionChain = pokemonRepository.getEvolutionChainOfPokemon(id);
            List<PokemonMoveDetails> pokemonMoves = pokemonRepository.getMovesOfPokemon(id);
            List<PokemonAbility> pokemonAbilities = pokemonRepository.getAbilitiesOfPokemon(id);
            return mapToPokemonDto(value, dexNumbers, evolutionChain, pokemonMoves, pokemonAbilities);
        });
    }

    @Override
    public List<PokemonDexSnapDto> getDexByRegion(String name) {
        PokedexRegion region = PokedexRegion.fromName(name);
        return mapToPokedexDto(pokemonRepository.getDexByRegion(region));
    }

    private PokemonDto mapToPokemonDto(Pokemon pokemon, List<DexNumbers> dexNumbers, List<EvolutionLine> evolutionChain, List<PokemonMoveDetails> pokemonMoves, List<PokemonAbility> abilities) {
        List<PokemonDto.MovesetDto> moveset = mapToMovesetDtoHelper(pokemonMoves);
        return new PokemonDto(
                pokemon.id(),
                pokemon.speciesId(),
                pokemon.name(),
                pokemon.gen(),
                pokemon.type1().getName(),
                pokemon.type2() == null ? null : pokemon.type2().getName(),
                abilities.stream().map(ability -> new PokemonDto.AbilityDto(
                        ability.abilityId(),
                        ability.abilityName(),
                        ability.isHidden(),
                        ability.genRemoved()
                )).toArray(PokemonDto.AbilityDto[]::new),
                pokemon.weight(),
                pokemon.height(),
                pokemon.genderRate(),
                new PokemonDto.StatsDto(
                        pokemon.hp(),
                        pokemon.atk(),
                        pokemon.def(),
                        pokemon.spatk(),
                        pokemon.spdef(),
                        pokemon.speed(),
                        pokemon.bst()
                ),
                pokemon.forms(),
                Arrays.stream(pokemon.dexEntries())
                        .map(dexEntry -> new PokemonDto.DexEntryDto(dexEntry.game(), dexEntry.entry()))
                        .toArray(PokemonDto.DexEntryDto[]::new),
                dexNumbers.stream()
                        .map(dex -> new PokemonDto.DexNumberDto(dex.region().getName(), dex.dexNumber()))
                        .toArray(PokemonDto.DexNumberDto[]::new),
                evolutionChain.stream()
                        .map(line -> new PokemonDto.EvolutionLineDto(
                                line.id(),
                                line.fromPokemon(),
                                line.fromDisplay(),
                                line.toPokemon(),
                                line.toDisplay(),
                                line.details(),
                                line.region(),
                                line.altForm()
                        )).toArray(PokemonDto.EvolutionLineDto[]::new),
                moveset.toArray(PokemonDto.MovesetDto[]::new)
        );
    }

    private List<PokemonDto.MovesetDto> mapToMovesetDtoHelper(List<PokemonMoveDetails> items) {
        items.sort(Comparator.comparing(PokemonMoveDetails::learnMethod));

        Map<VersionGroup, List<PokemonMoveDetails>> groupedByVersion = items.stream()
                .collect(Collectors.groupingBy(PokemonMoveDetails::versionGroup));

        return groupedByVersion.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(VersionGroup.ORDER))
                .map(entry -> {
                    VersionGroup versionGroup = entry.getKey();
                    List<PokemonMoveDetails> versionMoves = entry.getValue();

                    Map<LearnMethod, List<PokemonMoveDetails>> groupedByMethod = versionMoves.stream()
                            .collect(Collectors.groupingBy(PokemonMoveDetails::learnMethod));

                    PokemonDto.MovesetDto.LearnMethodSets[] learnMethodSets = groupedByMethod.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey(LearnMethod.ORDER))
                            .map(methodEntry -> {
                                LearnMethod learnMethod = methodEntry.getKey();
                                List<PokemonMoveDetails> methodMoves = methodEntry.getValue();
                                PokemonDto.MovesetDto.LearnMethodSets.Move[] moves = methodMoves.stream()
                                        .map(move -> new PokemonDto.MovesetDto.LearnMethodSets.Move(
                                                move.name(),
                                                move.moveClass().name().toLowerCase(),
                                                move.movePower(),
                                                move.moveAccuracy(),
                                                move.movePP(),
                                                move.levelLearned()
                                        ))
                                        .toArray(PokemonDto.MovesetDto.LearnMethodSets.Move[]::new);

                                return new PokemonDto.MovesetDto.LearnMethodSets(learnMethod.getName(), moves);
                            }).toArray(PokemonDto.MovesetDto.LearnMethodSets[]::new);

                    return new PokemonDto.MovesetDto(versionGroup.getVersionName(), learnMethodSets);
                }).collect(Collectors.toList());
    }

    private List<PokemonDexSnapDto> mapToPokedexDto(List<PokemonDexSnap> dexByRegion) {
        return dexByRegion.stream().map(entry -> new PokemonDexSnapDto(
                entry.dexNumber(),
                entry.id(),
                entry.name(),
                entry.type1().getName(),
                entry.type2() == null ? null : entry.type2().getName()
        )).toList();
    }
}
