package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.*;
import com.rishi.PokePedia.model.enums.PokedexRegion;
import com.rishi.PokePedia.model.enums.VersionGroup;

import java.util.List;
import java.util.Optional;

public interface PokemonService {
    Optional<PokemonDto> getPokemonById(Integer id);
    Optional<PokemonDto> getPokemonByName(String name);
    Optional<SpeciesDto> getPokemonFromSpeciesId(Integer id);
    Optional<SpeciesDto> getPokemonFromSpeciesName(String name);

    List<PokedexDto> getDexByVersion(String name);

    List<PokemonSnapDto> getDexByRegion(String name);

    List<PokemonSnapDto> getDexByRegion(PokedexRegion region);

    List<TeamBuildingDto> getTeamCandidates(String versionGroupName);
}
