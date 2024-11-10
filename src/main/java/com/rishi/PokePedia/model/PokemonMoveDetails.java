package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.LearnMethod;
import com.rishi.PokePedia.model.enums.MoveClass;
import com.rishi.PokePedia.model.enums.Type;
import com.rishi.PokePedia.model.enums.VersionGroup;

public record PokemonMoveDetails (
        Integer moveId,
        String name,
        Type type,
        MoveClass moveClass,
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        LearnMethod learnMethod,
        Integer levelLearned,
        VersionGroup versionGroup
){}
