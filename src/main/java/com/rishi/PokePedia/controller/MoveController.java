package com.rishi.PokePedia.controller;

import com.rishi.PokePedia.dto.MoveDto;
import com.rishi.PokePedia.dto.MoveSnapDto;
import com.rishi.PokePedia.service.MoveService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/move")
public class MoveController {
    private final MoveService moveService;

    public MoveController(MoveService moveService) { this.moveService = moveService; }

    @GetMapping("/")
    public List<MoveSnapDto> getMoves() {
        return moveService.getMoves();
    }

    @GetMapping("/{id:\\d+}")
    public MoveDto getMove(@PathVariable Integer id) {
        return moveService.getMoveById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "No move exists of id " + id ));
    }

    @GetMapping("/{name:[a-zA-Z\\-]+}")
    public MoveDto getMove(@PathVariable String name) {
        return moveService.getMoveByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No move exists of name " + name ));
    }
}
