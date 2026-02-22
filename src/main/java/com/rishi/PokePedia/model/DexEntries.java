package com.rishi.PokePedia.model;

import com.rishi.PokePedia.model.enums.Game;

public record DexEntries(
    Game game,
    String entry
) { }
