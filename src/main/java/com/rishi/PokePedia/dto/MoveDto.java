package com.rishi.PokePedia.dto;

import java.util.List;

public record MoveDto(
        Integer id,
        String name,
        String type,
        Integer gen,
        String moveClass,
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        List<PastMoveValues> pastMoveValues,
        List<Description> descriptions,
        List<Pokemon> pokemon
) {
    public record PastMoveValues (
            Integer movePower,
            Integer moveAccuracy,
            Integer movePP,
            String[] versionGroups
    ){}
    public record Description(
            String[] versionGroups,
            String description
    ){}

    public record Pokemon (
            Integer speciesId,
            Integer id,
            String name,
            String type1,
            String type2
    ){}
}
