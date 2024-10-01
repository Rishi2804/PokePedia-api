package com.rishi.PokePedia.controller;

import com.rishi.PokePedia.dto.PokemonDexSnapDto;
import com.rishi.PokePedia.dto.PokemonDto;
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

    @GetMapping("/health")
    public String getHealthCheck() {
        return "API functional";
    }

    @GetMapping("/pokemon/{id}")
    public PokemonDto getPokemon(@PathVariable Integer id) {
        return pokemonService.getPokemonById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon exists of id " + id));
    }

    @GetMapping("/pokedex/{name}")
    public List<PokemonDexSnapDto> getDex(@PathVariable String name) {
        try {
            return pokemonService.getDexByRegion(name);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No pokedex exists of name " + name);
        }
    }

}
