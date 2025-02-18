package com.rishi.PokePedia.controller;

import com.rishi.PokePedia.dto.*;
import com.rishi.PokePedia.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PokemonController {
    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon/{id:\\d+}")
    public PokemonDto getPokemon(@PathVariable Integer id) {
        return pokemonService.getPokemonById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon exists of id " + id));
    }

    @GetMapping("/pokemon/{name:[a-zA-Z\\-]+}")
    public PokemonDto getPokemon(@PathVariable String name) {
        return pokemonService.getPokemonByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No pokemon exists of name " + name));
    }

    @GetMapping("/species/{id:\\d+}")
    public SpeciesDto getSpeciesList(@PathVariable Integer id) {
        return pokemonService.getPokemonFromSpeciesId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No species exists of id " + id));
    }

    @GetMapping("/species/{name:[a-zA-Z\\-]+}")
    public SpeciesDto getSpeciesList(@PathVariable String name) {
        return pokemonService.getPokemonFromSpeciesName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No species exists of name " + name));
    }

    @GetMapping("/pokedex/{name}")
    public List<PokedexDto> getDex(@PathVariable String name) {
        try {
            return pokemonService.getDexByVersion(name);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No pokedex exists of name " + name);
        }
    }

    @GetMapping("/team-building/{version}")
    public List<TeamBuildingDto> getCandidates(@PathVariable String version) {
        try {
            return pokemonService.getTeamCandidates(version);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Version Group exists of name " + version);
        }

    }

}
