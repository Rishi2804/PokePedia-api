package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.Game;
import com.rishi.PokePedia.model.enums.Type;

public record Pokemon(
        Integer id,
        Integer speciesId,
        String name,
        Integer gen,
        Type type1,
        Type type2,
        Float weight,
        Float height,
        Integer genderRate,
        Integer hp,
        Integer atk,
        Integer def,
        Integer spatk,
        Integer spdef,
        Integer speed,
        Integer bst,
        String[] forms,
        DexEntry[] dexEntries

) {
    public record DexEntry(Game game, String entry) {}
}
