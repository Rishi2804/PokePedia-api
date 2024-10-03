package com.rishi.PokePedia.model;

public record PokemonAbility(
                Integer abilityId,
                String abilityName,
                Boolean isHidden,
                Integer genRemoved
) {
}
