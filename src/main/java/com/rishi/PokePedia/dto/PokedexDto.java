package com.rishi.PokePedia.dto;

import java.util.List;

public record PokedexDto(
        String name,
        List<PokemonSnapDto> pokemon
)
{ }
