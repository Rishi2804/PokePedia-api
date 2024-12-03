package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.AbilityDto;
import com.rishi.PokePedia.dto.AbilitySnapDto;

import java.util.List;
import java.util.Optional;

public interface AbilityService {
    List<AbilitySnapDto> getAbilities();

    Optional<AbilityDto> getAbilityById(Integer id);
    Optional<AbilityDto> getAbilityByName(String name);
}
