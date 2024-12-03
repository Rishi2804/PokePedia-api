package com.rishi.PokePedia.controller;

import com.rishi.PokePedia.dto.AbilityDto;
import com.rishi.PokePedia.dto.AbilitySnapDto;
import com.rishi.PokePedia.service.AbilityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ability")
public class AbilityController {
    private final AbilityService abilityService;
    public AbilityController(AbilityService abilityService) { this.abilityService = abilityService; }
    @GetMapping("/")
    public List<AbilitySnapDto> getAbilities() {
        return abilityService.getAbilities();
    }

    @GetMapping("/{id:\\d+}")
    public AbilityDto getAbility(@PathVariable Integer id) {
        return abilityService.getAbilityById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No ability exists of id " + id ));
    }

    @GetMapping("/{name:[a-zA-Z\\-]+}")
    public AbilityDto getAbility(@PathVariable String name) {
        return abilityService.getAbilityByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No ability exists of name " + name ));
    }

}
