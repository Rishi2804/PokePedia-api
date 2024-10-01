package com.rishi.PokePedia.model;

public record DexNumbers (
        Integer dexNumber,
        Integer speciesId,
        PokedexRegion region,
        Integer defaultVariate,
        Integer[] altVariates
){ }
