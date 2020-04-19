package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.AquariumDtoAssembler;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.service.AquariumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aquariums")
@AllArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final AquariumDtoAssembler aquariumDtoAssembler;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<AquariumResponseDto> findAll() {
        Iterable<Aquarium> all = aquariumService.findAll();
        return aquariumDtoAssembler.toCollectionModel(all);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AquariumResponseDto findById(@PathVariable("id") Integer id) {
        Aquarium aquarium = aquariumService.findBy(id);
        return aquariumDtoAssembler.toModel(aquarium);
    }
}
