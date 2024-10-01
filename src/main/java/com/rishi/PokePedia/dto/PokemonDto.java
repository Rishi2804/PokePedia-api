package com.rishi.PokePedia.dto;
public record PokemonDto(
        Integer id,
        Integer speciesId,
        String name,
        Integer gen,
        String type1,
        String type2,
        Float weight,
        Float height,
        Integer genderRate,
        StatsDto stats,
        String[] forms,
        DexEntryDto[] dexEntries,
        DexNumberDto[] dexNumbers,
        EvolutionLineDto[] evolutionChain

) {
    public record StatsDto(
            Integer hp,
            Integer atk,
            Integer def,
            Integer spatk,
            Integer spdef,
            Integer speed,
            Integer bst
    ) {}
    public record DexEntryDto(String game, String entry) {}
    public record DexNumberDto(String dexName, Integer dexNumber) {}
    public record EvolutionLineDto(
            Integer id,
            Integer fromPokemon,
            String fromDisplay,
            Integer toPokemon,
            String toDisplay,
            String[] details,
            String region,
            Integer altForm
    ){}
}
