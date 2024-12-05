package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.Type;

public record PokemonSnap(
        Integer dexNumber,
        Integer speciesId,
        Integer id,
        String name,
        Integer gen,
        Type type1,
        Type type2
) {
}
