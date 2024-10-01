package com.rishi.PokePedia.model;

public record PokemonDexSnap(
        Integer dexNumber,
        Integer id,
        String name,
        Type type1,
        Type type2
) {
}
