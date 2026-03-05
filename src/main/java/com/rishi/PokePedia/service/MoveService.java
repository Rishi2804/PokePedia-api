package com.rishi.PokePedia.service;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.dto.MoveSnapDto;

import java.util.ArrayList;
import java.util.Optional;

public interface MoveService {
    ArrayList<MoveSnapDto> getMoves();
    Optional<MoveDto> getMoveById(Integer id);
    Optional<MoveDto> getMoveByName(String name);
}
