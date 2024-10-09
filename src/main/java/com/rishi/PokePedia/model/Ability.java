package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.VersionGroup;

import java.util.List;

public record Ability(
        Integer id,
        String name,
        Integer gen,
        List<Description> descriptions
) {
    public record Description(
            String entry,
            VersionGroup[] groups
    ){}
}
