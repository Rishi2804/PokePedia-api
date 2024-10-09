package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.Type;

public record PokemonSnap(
        Integer dexNumber,
        Integer id,
        String name,
        Type type1,
        Type type2
) {
}
