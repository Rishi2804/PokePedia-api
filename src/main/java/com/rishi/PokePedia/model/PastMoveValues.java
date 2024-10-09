package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.VersionGroup;

public record PastMoveValues (
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        VersionGroup[] versionGroups
){}
