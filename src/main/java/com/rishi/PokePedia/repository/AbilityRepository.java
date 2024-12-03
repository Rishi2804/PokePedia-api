package com.rishi.PokePedia.repository;

import com.rishi.PokePedia.model.Ability;
import com.rishi.PokePedia.model.AbilitySnap;
import com.rishi.PokePedia.model.PokemonSnap;

import java.util.List;
import java.util.Optional;

public interface AbilityRepository {
    List<AbilitySnap> getAbilities();
    Optional<Ability> getAbilityById(Integer id);
    Optional<Ability> getAbilityByName(String name);
    List<PokemonSnap> getPokemonLearnable(Integer id);
}
