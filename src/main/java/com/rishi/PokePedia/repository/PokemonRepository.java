package com.rishi.PokePedia.repository;

import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.model.enums.PokedexRegion;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    // Singular Pokemon Methods
    Optional<Pokemon> getPokemonById(Integer id);
    Optional<Pokemon> getPokemonByName(String name);
    List<DexNumbers> getDexNumbersFromPokemon(Integer id);
    List<EvolutionLine> getEvolutionChainOfPokemon(Integer id);
    List<PokemonMoveDetails> getMovesOfPokemon(Integer id);
    List<PokemonAbility> getAbilitiesOfPokemon(Integer id);
    Optional<String> getSpeciesName(Integer id);
    Optional<Integer> getSpeciesId(String name);
    List<Integer> getPokemonIdsFromSpeciesId(Integer id);

    // Pokedex List
    List<PokemonSnap> getDexByRegion(PokedexRegion pokedexRegion);
}
