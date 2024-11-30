package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.MoveClass;
import com.rishi.PokePedia.model.enums.Type;
import com.rishi.PokePedia.model.enums.VersionGroup;

import java.util.List;

public record Move (
        Integer id,
        String name,
        Type type,
        Integer gen,
        MoveClass moveClass,
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        String effect,
        List<Description> descriptions
) {
    public record Description(
            String entry,
            VersionGroup[] versionGroups
    ){}
}
