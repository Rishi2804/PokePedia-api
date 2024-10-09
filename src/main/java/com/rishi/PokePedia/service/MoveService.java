package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.MoveDto;

import java.util.Optional;

public interface MoveService {
    Optional<MoveDto> getMoveById(Integer id);
    Optional<MoveDto> getMoveByName(String name);
}
