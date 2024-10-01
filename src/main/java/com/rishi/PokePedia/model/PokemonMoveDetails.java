package com.rishi.PokePedia.model;

public record PokemonMoveDetails(
        Integer moveId,
        String name,
        Integer movePower,
        MoveClass moveClass,
        Integer moveAccuracy,
        Integer movePP,
        LearnMethod learnMethod,
        Integer levelLearned,
        VersionGroup versionGroup

) {
}
