package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.AbilityDto;
import com.rishi.PokePedia.model.Ability;
import com.rishi.PokePedia.model.PokemonSnap;
import com.rishi.PokePedia.repository.AbilityRepository;
import com.rishi.PokePedia.service.AbilityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.rishi.PokePedia.service.utils.formatName;

@Service
public class AbilityServiceImpl implements AbilityService {

    private final AbilityRepository abilityRepository;

    public AbilityServiceImpl(AbilityRepository abilityRepository) { this.abilityRepository = abilityRepository; }

    @Override
    public Optional<AbilityDto> getAbilityById(Integer id) {
        Optional<Ability> ability = abilityRepository.getAbilityById(id);
        return ability.map(val -> {
           List<PokemonSnap> pokemon = abilityRepository.getPokemonLearnable(id);
           return mapToAbilityDto(val, pokemon);
        });
    }

    @Override
    public Optional<AbilityDto> getAbilityByName(String name) {
        Optional<Ability> ability = abilityRepository.getAbilityByName(name);
        return ability.map(val -> {
            List<PokemonSnap> pokemon = abilityRepository.getPokemonLearnable(val.id());
            return mapToAbilityDto(val, pokemon);
        });
    }

    private static AbilityDto mapToAbilityDto(Ability ability, List<PokemonSnap> pokemon) {
        return new AbilityDto(
                ability.id(),
                formatName(ability.name(), false),
                ability.gen(),
                ability.descriptions().stream().map(description -> new AbilityDto.Description(
                        description.entry(),
                        Arrays.stream(description.groups()).map(group -> group.name()).toArray(String[]::new)
                )).toList(),
                pokemon.stream().map(mon -> new AbilityDto.Pokemon(
                        mon.id(), formatName(mon.name(), true), mon.type1().name(), mon.type2() == null ? null : mon.type2().name()
                )).toList()
        );
    }
}
