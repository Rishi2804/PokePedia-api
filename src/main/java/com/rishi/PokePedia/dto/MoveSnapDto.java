package com.rishi.PokePedia.dto;

public record MoveSnapDto(
        Integer id,
        String name,
        String type,
        String moveClass,
        Integer power,
        Integer accuracy,
        Integer pp,
        Integer gen
){}