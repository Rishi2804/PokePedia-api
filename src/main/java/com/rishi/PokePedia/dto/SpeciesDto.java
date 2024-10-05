package com.rishi.PokePedia.dto;

import java.util.List;

public record SpeciesDto(
        Integer id,
        String name,
        List<PokemonDto> pokemon
) { }
