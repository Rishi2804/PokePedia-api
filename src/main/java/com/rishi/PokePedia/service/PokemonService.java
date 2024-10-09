package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.PokemonSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;
import com.rishi.PokePedia.dto.SpeciesDto;

import java.util.List;
import java.util.Optional;

public interface PokemonService {
    Optional<PokemonDto> getPokemonById(Integer id);
    Optional<PokemonDto> getPokemonByName(String name);
    Optional<SpeciesDto> getPokemonFromSpeciesId(Integer id);
    Optional<SpeciesDto> getPokemonFromSpeciesName(String name);
    List<PokemonSnapDto> getDexByRegion(String name);
}
