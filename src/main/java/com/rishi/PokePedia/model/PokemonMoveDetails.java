package com.rishi.PokePedia.model;

public record PokemonMoveDetails (
        Integer moveId,
        String name,
        MoveClass moveClass,
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        LearnMethod learnMethod,
        Integer levelLearned,
        VersionGroup versionGroup
){}
