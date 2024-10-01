package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.PokemonDexSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.repository.PokemonRepository;
import com.rishi.PokePedia.service.PokemonService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public Optional<PokemonDto> getPokemonById(Integer id) {
        Optional<Pokemon> pokemon = pokemonRepository.getPokemonById(id);
        List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
        List<EvolutionLine> evolutionChain = pokemonRepository.getEvolutionChainOfPokemon(id);
        return pokemon.map(value -> mapToPokemonDto(value, dexNumbers, evolutionChain));
    }

    public List<PokemonDexSnapDto> getDexByRegion(String name) {
        PokedexRegion region = PokedexRegion.fromName(name);
        return mapToPokedexDto(pokemonRepository.getDexByRegion(region));
    }

    private PokemonDto mapToPokemonDto(Pokemon pokemon, List<DexNumbers> dexNumbers, List<EvolutionLine> evolutionChain) {
        return new PokemonDto(
                pokemon.id(),
                pokemon.speciesId(),
                pokemon.name(),
                pokemon.gen(),
                pokemon.type1().getName(),
                pokemon.type2() == null ? null : pokemon.type2().getName(),
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
                        ) ).toArray(PokemonDto.EvolutionLineDto[]::new)
        );
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
