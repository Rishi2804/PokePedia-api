package com.rishi.PokePedia.dto;

import java.util.List;

public record AbilityDto(
        Integer id,
        String name,
        Integer gen,
        String effect,
        List<Description> descriptions,
        List<Pokemon> pokemon
) {
    public record Description(

            String description,
            String[] versionGroups
    ) {}

    public record Pokemon(
            Integer speciesId,
            Integer pokemonId,
            String name,
            String type1,
            String type2
    ){}
}
