package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.PokemonDexSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;
import com.rishi.PokePedia.model.DexNumbers;
import com.rishi.PokePedia.model.PokedexRegion;
import com.rishi.PokePedia.model.Pokemon;
import com.rishi.PokePedia.model.PokemonDexSnap;
import com.rishi.PokePedia.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public PokemonDto getPokemonById(Integer id) {
        Pokemon pokemon = pokemonRepository.getPokemonById(id);
        List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
        return mapToPokemonDto(pokemon, dexNumbers);
    }

    public List<PokemonDexSnapDto> getDexByRegion(String name) {
        PokedexRegion region = PokedexRegion.fromName(name);
        return mapToPokedexDto(pokemonRepository.getDexByRegion(region));
    }

    private PokemonDto mapToPokemonDto(Pokemon pokemon, List<DexNumbers> dexNumbers) {
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
                        .toArray(PokemonDto.DexNumberDto[]::new)
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
