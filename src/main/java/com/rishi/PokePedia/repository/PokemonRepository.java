package com.rishi.PokePedia.repository;

import com.rishi.PokePedia.model.*;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    // Singular Pokemon Methods
    Optional<Pokemon> getPokemonById(Integer id);
    List<DexNumbers> getDexNumbersFromPokemon(Integer id);
    List<EvolutionLine> getEvolutionChainOfPokemon(Integer id);
    List<PokemonMoveDetails> getMovesOfPokemon(Integer id);

    // Pokedex List
    List<PokemonDexSnap> getDexByRegion(PokedexRegion pokedexRegion);
}
