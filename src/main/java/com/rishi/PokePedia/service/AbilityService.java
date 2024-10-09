package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.AbilityDto;

import java.util.Optional;

public interface AbilityService {
    Optional<AbilityDto> getAbilityById(Integer id);
    Optional<AbilityDto> getAbilityByName(String name);
}
