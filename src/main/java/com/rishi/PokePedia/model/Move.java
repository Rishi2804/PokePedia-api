package com.rishi.PokePedia.model;

import java.util.List;

public record Move (
        Integer id,
        String name,
        MoveClass moveClass,
        Integer movePower,
        Integer moveAccuracy,
        Integer movePP,
        List<Description> descriptions
) {
    public record Description(
            String entry,
            VersionGroup[] versionGroups
    ){}
}
