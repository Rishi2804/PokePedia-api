package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.dto.MoveSnapDto;

import java.util.List;
import java.util.Optional;

public interface MoveService {
    List<MoveSnapDto> getMoves();
    Optional<MoveDto> getMoveById(Integer id);
    Optional<MoveDto> getMoveByName(String name);
}
