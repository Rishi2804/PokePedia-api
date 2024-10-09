package com.rishi.PokePedia.repository;

import com.rishi.PokePedia.model.Move;
import com.rishi.PokePedia.model.PokemonSnap;

import java.util.List;
import java.util.Optional;

public interface MoveRepository {
    Optional<Move> getMoveById(Integer id);
    Optional<Move> getMoveByName(String name);
    List<PokemonSnap> getPokemonLearnable(Integer id);
}
