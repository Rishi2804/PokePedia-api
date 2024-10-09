package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.PokedexRegion;

public record DexNumbers (
        Integer dexNumber,
        PokedexRegion region
){ }
