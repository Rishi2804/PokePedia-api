package com.rishi.PokePedia.dto;

public record PokemonSnapDto(
        Integer dexNumber,
        Integer speciesId,
        Integer pokemonId,
        String name,
        String type1,
        String type2
) {
}
