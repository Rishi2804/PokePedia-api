package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.MoveClass;
import com.rishi.PokePedia.model.enums.Type;

public record MoveSnap (
        Integer id,
        String name,
        Type type,
        MoveClass moveClass,
        Integer power,
        Integer accuracy,
        Integer pp,
        Integer levelLearned
){}
