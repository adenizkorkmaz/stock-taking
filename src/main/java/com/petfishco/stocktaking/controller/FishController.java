package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.FishDtoAssembler;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.FishRequestDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import com.petfishco.stocktaking.service.FishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/fishes")
@AllArgsConstructor
@Slf4j
public class FishController {

    private final FishService fishService;
    private final FishDtoAssembler fishDtoAssembler;

    @PostMapping
    public ResponseEntity<FishResponseDto> createNewGame(@Valid @RequestBody FishRequestDto fishRequestDto) {
        Fish fish = fishService.create(fishRequestDto);
        return new ResponseEntity<>(fishDtoAssembler.toModel(fish), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishResponseDto> findById(@PathVariable("id") Integer id) {
        Fish fish = fishService.findBy(id);
        return new ResponseEntity<>(fishDtoAssembler.toModel(fish), HttpStatus.OK);
    }

    @GetMapping("/aquarium/{aquariumId}")
    public ResponseEntity<CollectionModel<FishResponseDto>> findByAquariumId(@PathVariable("aquariumId") Integer aquariumId) {
        Iterable<Fish> fishIterable = fishService.findByAquarium(aquariumId);
        return new ResponseEntity<>(fishDtoAssembler.toCollectionModel(fishIterable, aquariumId), HttpStatus.OK);
    }
}
