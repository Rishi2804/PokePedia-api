package com.rishi.PokePedia.repository;

import com.rishi.PokePedia.model.*;

import java.util.List;
import java.util.Optional;

public interface MoveRepository {
    List<MoveSnap> getMoves();
    Optional<Move> getMoveById(Integer id);
    Optional<Move> getMoveByName(String name);
    List<MovePokemonLearnable> getPokemonLearnable(Integer id);
    List<PastMoveValues> getPastValues(Integer id);
}
