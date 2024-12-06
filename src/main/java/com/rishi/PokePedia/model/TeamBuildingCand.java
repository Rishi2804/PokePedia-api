package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.Type;

public record TeamBuildingCand(
        Integer id,
        String name,
        Type type1,
        Type type2,
        Integer gen
) {
}
