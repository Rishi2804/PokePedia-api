package com.rishi.PokePedia.dto;

import java.util.List;

public record AbilityDto(
        Integer id,
        String name,
        Integer gen,
        List<Description> descriptions,
        List<Pokemon> pokemon
) {
    public record Description(

            String description,
            String[] versionGroups
    ) {}

    public record Pokemon(
            Integer id,
            String name,
            String type1,
            String type2
    ){}
}
