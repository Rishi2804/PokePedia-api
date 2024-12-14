package com.rishi.PokePedia.service.impl;

import com.rishi.PokePedia.dto.*;
import com.rishi.PokePedia.model.*;
import com.rishi.PokePedia.model.enums.LearnMethod;
import com.rishi.PokePedia.model.enums.PokedexRegion;
import com.rishi.PokePedia.model.enums.PokedexVersion;
import com.rishi.PokePedia.model.enums.VersionGroup;
import com.rishi.PokePedia.repository.PokemonRepository;
import com.rishi.PokePedia.service.PokemonService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.rishi.PokePedia.service.utils.formatName;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public Optional<PokemonDto> getPokemonById(Integer id) {
        Optional<Pokemon> pokemon = pokemonRepository.getPokemonById(id);
        List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
        List<EvolutionLine> evolutionChain = pokemonRepository.getEvolutionChainOfPokemon(id);
        List<PokemonMoveDetails> pokemonMoves = pokemonRepository.getMovesOfPokemon(id, null);
        List<PokemonAbility> pokemonAbilities = pokemonRepository.getAbilitiesOfPokemon(id);
        return pokemon.map(value -> mapToPokemonDto(value, dexNumbers, evolutionChain, pokemonMoves, pokemonAbilities));
    }

    @Override
    public Optional<PokemonDto> getPokemonByName(String name) {
        Optional<Pokemon> pokemon = pokemonRepository.getPokemonByName(name);
        return pokemon.map(value -> {
            Integer id = value.id();
            List<DexNumbers> dexNumbers = pokemonRepository.getDexNumbersFromPokemon(id);
            List<EvolutionLine> evolutionChain = pokemonRepository.getEvolutionChainOfPokemon(id);
            List<PokemonMoveDetails> pokemonMoves = pokemonRepository.getMovesOfPokemon(id, null);
            List<PokemonAbility> pokemonAbilities = pokemonRepository.getAbilitiesOfPokemon(id);
            return mapToPokemonDto(value, dexNumbers, evolutionChain, pokemonMoves, pokemonAbilities);
        });
    }

    @Override
    public Optional<SpeciesDto> getPokemonFromSpeciesId(Integer id) {
        Integer finalId;

        if (id > 10000) {
            Optional<Integer> optId = pokemonRepository.getSpeciesIdFromPokemon(id);

            if (optId.isPresent()) {
                finalId = optId.get();
            } else {
                return Optional.empty();
            }
        } else {
            finalId = id;
        }
        Optional<String> name = pokemonRepository.getSpeciesName(finalId);
        return name.map(val -> {
            List<Integer> pokemonIds = pokemonRepository.getPokemonIdsFromSpeciesId(finalId);
            List<PokemonDto> pokemonDtos = new ArrayList<>();
            for (Integer pid : pokemonIds) pokemonDtos.add(getPokemonById(pid).get());
            return new SpeciesDto(finalId, val, pokemonDtos);
        });
    }

    @Override
    public Optional<SpeciesDto> getPokemonFromSpeciesName(String name) {
        Optional<Integer> id = pokemonRepository.getSpeciesId(name);
        return id.map(val -> {
            List<Integer> pokemonIds = pokemonRepository.getPokemonIdsFromSpeciesId(val);
            List<PokemonDto> pokemonDtos = new ArrayList<>();
            for (Integer pid : pokemonIds) pokemonDtos.add(getPokemonById(pid).get());
            return new SpeciesDto(val, name, pokemonDtos);
        });
    }

    @Override
    public List<PokedexDto> getDexByVersion(String name) {
        List<PokedexDto> dexes = new ArrayList<>();

        PokedexVersion version = PokedexVersion.fromString(name);
        if (version == PokedexVersion.DIAMOND_PEARL_PLATINUM) {
            List<PokemonSnapDto> fulldex = getDexByRegion(version.getRegions()[0]);
            int seperator = 0;
            for (int i = 0; i < fulldex.size(); i++) {
                if (fulldex.get(i).dexNumber() == 152) {
                    seperator = i;
                    break;
                }
            }
            List<PokemonSnapDto> originalSinnoh = fulldex.subList(0, seperator);
            List<PokemonSnapDto> extendedSinnoh = fulldex.subList(seperator, fulldex.size());

            dexes.add(new PokedexDto("Original Sinnoh", originalSinnoh));
            dexes.add(new PokedexDto("Platinum Expansion", extendedSinnoh));
            return dexes;
        }

        if (version == PokedexVersion.NATIONAL) {
            List<PokemonSnapDto> fulldex = getDexByRegion(version.getRegions()[0]);
            int[] seperatorNums = {1, 152, 252, 387, 494, 650, 722, 810, 906};
            int[] seperatorIndicies = Arrays.stream(seperatorNums).map((num) -> {
                for (int i = 0; i < fulldex.size(); i++) {
                    if (fulldex.get(i).dexNumber() == num) {
                        return i;
                    }
                }
                return -1;
            }).toArray();

            int n = seperatorIndicies.length;
            for (int i = 1; i < n; i++) {
                dexes.add(new PokedexDto("Gen " + i, fulldex.subList(seperatorIndicies[i-1], seperatorIndicies[i])));
            }
            dexes.add(new PokedexDto("Gen " + n, fulldex.subList(seperatorIndicies[n-1], fulldex.size())));

            return dexes;
        }

        for (PokedexRegion region : version.getRegions()) {
            dexes.add(new PokedexDto(
                    region.getName(),
                    getDexByRegion(region)
            ));
        }
        return dexes;
    }

    @Override
    public List<PokemonSnapDto> getDexByRegion(String name) {
        PokedexRegion region = PokedexRegion.fromName(name);
        return mapToPokedexDto(pokemonRepository.getDexByRegion(region));
    }

    @Override
    public List<PokemonSnapDto> getDexByRegion(PokedexRegion region) {
        return mapToPokedexDto(pokemonRepository.getDexByRegion(region));
    }

    @Override
    public List<TeamBuildingDto> getTeamCandidates(String versionGroupName) {
        VersionGroup versionGroup = VersionGroup.fromName(versionGroupName);
        PokedexRegion[] regions = versionGroup.getRegions();
        List<TeamBuildingDto> res = new ArrayList<>();
        Set<Integer> seenPokemonIds = new HashSet<>();
        for (PokedexRegion region : regions) {
            List<TeamBuildingCand> teamCands = (pokemonRepository.getTeamCandidates(region));
            teamCands.forEach(item -> seenPokemonIds.add(item.id()));
            res.add(new TeamBuildingDto(
                    region.getName(),
                    mapToTeamCandidateDto(teamCands, versionGroup)
            ));
        }
        List<TeamBuildingCand> nationalCands = pokemonRepository.getTeamCandidatesNational(versionGroup);
        nationalCands = nationalCands.stream().filter(item -> !seenPokemonIds.contains(item.id())).toList();
        if (versionGroup != VersionGroup.NATIONAL && nationalCands.size() > 0) {
            res.add(new TeamBuildingDto(
                    "National",
                    mapToTeamCandidateDto(nationalCands, versionGroup)
            ));
        }

        return res;
    }

    private PokemonDto mapToPokemonDto(Pokemon pokemon, List<DexNumbers> dexNumbers, List<EvolutionLine> evolutionChain, List<PokemonMoveDetails> pokemonMoves, List<PokemonAbility> abilities) {
        List<PokemonDto.MovesetDto> moveset = mapToMovesetDtoHelper(pokemonMoves);
        return new PokemonDto(
                pokemon.id(),
                pokemon.speciesId(),
                formatName(pokemon.name(), true),
                pokemon.gen(),
                pokemon.type1().name(),
                pokemon.type2() == null ? null : pokemon.type2().name(),
                abilities.stream().map(ability -> new PokemonDto.AbilityDto(
                        ability.abilityId(),
                        formatName(ability.abilityName(), false),
                        ability.isHidden(),
                        ability.genRemoved()
                )).toArray(PokemonDto.AbilityDto[]::new),
                pokemon.weight(),
                pokemon.height(),
                pokemon.genderRate(),
                new PokemonDto.StatsDto(
                        pokemon.hp(),
                        pokemon.atk(),
                        pokemon.def(),
                        pokemon.spatk(),
                        pokemon.spdef(),
                        pokemon.speed(),
                        pokemon.bst()
                ),
                pokemon.forms(),
                Arrays.stream(pokemon.dexEntries())
                        .map(dexEntry -> new PokemonDto.DexEntryDto(dexEntry.game().name(), dexEntry.entry()))
                        .toArray(PokemonDto.DexEntryDto[]::new),
                dexNumbers.stream()
                        .map(dex -> new PokemonDto.DexNumberDto(dex.region().name(), dex.dexNumber()))
                        .toArray(PokemonDto.DexNumberDto[]::new),
                evolutionChain.stream()
                        .map(line -> new PokemonDto.EvolutionLineDto(
                                line.id(),
                                line.fromPokemon(),
                                formatName(line.fromDisplay(), true),
                                line.toPokemon(),
                                formatName(line.toDisplay(), true),
                                line.details(),
                                line.region(),
                                line.altForm()
                        )).toArray(PokemonDto.EvolutionLineDto[]::new),
                moveset.toArray(PokemonDto.MovesetDto[]::new)
        );
    }

    private List<PokemonDto.MovesetDto> mapToMovesetDtoHelper(List<PokemonMoveDetails> items) {
        items.sort(Comparator.comparing(PokemonMoveDetails::learnMethod));

        Map<VersionGroup, List<PokemonMoveDetails>> groupedByVersion = items.stream()
                .collect(Collectors.groupingBy(PokemonMoveDetails::versionGroup));

        return groupedByVersion.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(VersionGroup.ORDER))
                .map(entry -> {
                    VersionGroup versionGroup = entry.getKey();
                    List<PokemonMoveDetails> versionMoves = entry.getValue();

                    Map<LearnMethod, List<PokemonMoveDetails>> groupedByMethod = versionMoves.stream()
                            .collect(Collectors.groupingBy(PokemonMoveDetails::learnMethod));

                    PokemonDto.MovesetDto.LearnMethodSets[] learnMethodSets = groupedByMethod.entrySet().stream()
                            .sorted(Map.Entry.comparingByKey(LearnMethod.ORDER))
                            .map(methodEntry -> {
                                LearnMethod learnMethod = methodEntry.getKey();
                                List<PokemonMoveDetails> methodMoves = methodEntry.getValue();
                                PokemonDto.MovesetDto.LearnMethodSets.Move[] moves = methodMoves.stream()
                                        .map(move -> new PokemonDto.MovesetDto.LearnMethodSets.Move(
                                                move.moveId(),
                                                formatName(move.name(), false),
                                                move.type().name(),
                                                move.moveClass().name(),
                                                move.movePower(),
                                                move.moveAccuracy(),
                                                move.movePP(),
                                                move.levelLearned()
                                        ))
                                        .sorted(Comparator.comparingInt(PokemonDto.MovesetDto.LearnMethodSets.Move::levelLearned))
                                        .toArray(PokemonDto.MovesetDto.LearnMethodSets.Move[]::new);

                                return new PokemonDto.MovesetDto.LearnMethodSets(learnMethod.name(), moves);
                            }).toArray(PokemonDto.MovesetDto.LearnMethodSets[]::new);

                    return new PokemonDto.MovesetDto(versionGroup.name(), learnMethodSets);
                }).collect(Collectors.toList());
    }

    private List<PokemonSnapDto> mapToPokedexDto(List<PokemonSnap> dexByRegion) {
        return dexByRegion.stream().map(entry -> new PokemonSnapDto(
                entry.dexNumber(),
                entry.speciesId(),
                entry.id(),
                formatName(entry.name(), true),
                entry.gen(),
                entry.type1().name(),
                entry.type2() == null ? null : entry.type2().name()
        )).toList();
    }

    private List<TeamBuildingDto.CandidateDto> mapToTeamCandidateDto(List<TeamBuildingCand> teamCands, VersionGroup versionGroup) {
        return teamCands.stream().map(cand -> {
            List<PokemonAbility> abilities = pokemonRepository.getAbilitiesOfPokemon(cand.id());
            List<PokemonMoveDetails> moves = pokemonRepository.getMovesOfPokemon(cand.id(), versionGroup);
            Set<Integer> seenMoveIds = new HashSet<>();
            List<TeamBuildingDto.CandidateDto.Ability> abilitiesDto = new ArrayList<>();

            Optional<PokemonAbility> matchedItem = abilities.stream()
                    .filter(item -> item.genRemoved() != null)  // Filter items where genRemoved() is not null
                    .findFirst();
            if (matchedItem.isPresent() && versionGroup.getGen() <= matchedItem.get().genRemoved()) {
                boolean hiddenRemoved = abilities.stream().anyMatch(item -> (item.genRemoved() != null && item.isHidden()));
                if (hiddenRemoved) {
                    for (PokemonAbility item : abilities) {
                        if (item.genRemoved() == null && item.isHidden()) continue;
                        else abilitiesDto.add(new TeamBuildingDto.CandidateDto.Ability(item.abilityId(), formatName(item.abilityName(), false)));
                    }
                } else { // special cases
                    if (cand.id() == 94) abilitiesDto.add(new TeamBuildingDto.CandidateDto.Ability(26, "Levitate"));
                    if (cand.id() == 275) abilitiesDto = abilities.stream()
                            .filter(item -> item.abilityId() != 274)
                            .map(item -> new TeamBuildingDto.CandidateDto.Ability(item.abilityId(), formatName(item.abilityName(), false)))
                            .toList();
                }
            } else {
                abilitiesDto = abilities.stream()
                        .filter(item -> item.gen() <= versionGroup.getGen())
                        .filter(item -> item.genRemoved() == null)
                        .map(item -> new TeamBuildingDto.CandidateDto.Ability(item.abilityId(), formatName(item.abilityName(), false)))
                        .toList();
            }
            return new TeamBuildingDto.CandidateDto(
                    cand.id(),
                    formatName(cand.name(), true),
                    cand.type1().name(),
                    cand.type2() != null ? cand.type2().name() : null,
                    cand.gen(),
                    cand.genderRate(),
                    abilitiesDto,
                    moves.stream()
                            .filter(item -> seenMoveIds.add(item.moveId()))
                            .map(item ->
                                    new TeamBuildingDto.CandidateDto.Move(item.moveId(), formatName(item.name(), false), item.type().name(), item.moveClass().name()))
                            .toList()
            );
        }).toList();
    }
}
