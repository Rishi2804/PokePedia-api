package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.PokemonDexSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;

import java.util.List;
import java.util.Optional;

public interface PokemonService {
    Optional<PokemonDto> getPokemonById(Integer id);
    Optional<PokemonDto> getPokemonByName(String name);
    List<PokemonDexSnapDto> getDexByRegion(String name);
}
