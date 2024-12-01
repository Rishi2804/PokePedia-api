package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.LearnMethod;
import com.rishi.PokePedia.model.enums.Type;

public record MovePokemonLearnable(
        Integer speciedId,
        Integer pokemonId,
        String name,
        Type type1,
        Type type2,
        LearnMethod method
) {}
