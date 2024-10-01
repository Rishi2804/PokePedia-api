package com.rishi.PokePedia.model;

public record EvolutionLine(
        Integer id,
        Integer fromPokemon,
        String fromDisplay,
        Integer toPokemon,
        String toDisplay,
        String[] details,
        String region,
        Integer altForm
) {
}
