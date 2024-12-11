package com.rishi.PokePedia.dto;

import java.util.List;

public record TeamBuildingDto(
        String listName,
        List<CandidateDto> pokemon
) {
    public record CandidateDto(
            Integer id,
            String name,
            String type1,
            String type2,
            Integer gen,
            Integer genderRate,
            List<Ability> abilities,
            List<Move> moves
    ) {
        public record Ability(
                Integer id,
                String name
        ) {}

        public record Move(
                Integer id,
                String name,
                String type,
                String moveClass
        ) {}
    }
}
