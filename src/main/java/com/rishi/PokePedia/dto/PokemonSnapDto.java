package com.rishi.PokePedia.dto;

public record PokemonSnapDto(
        Integer dexNumber,
        Integer speciesId,
        Integer pokemonId,
        String name,
        Integer gen,
        String type1,
        String type2
) {
}
