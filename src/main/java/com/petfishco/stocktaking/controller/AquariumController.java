package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.AquariumDtoAssembler;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.service.AquariumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aquariums")
@AllArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final AquariumDtoAssembler aquariumDtoAssembler;

    @GetMapping
    public ResponseEntity<CollectionModel<AquariumResponseDto>> findAll() {
        Iterable<Aquarium> all = aquariumService.findAll();
        return new ResponseEntity<>(aquariumDtoAssembler.toCollectionModel(all), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AquariumResponseDto> findById(@PathVariable("id") Integer id) {
        Aquarium aquarium = aquariumService.findBy(id);
        return new ResponseEntity<>(aquariumDtoAssembler.toModel(aquarium), HttpStatus.OK);
    }
}
