package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.FishDtoAssembler;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
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
    @ResponseStatus(HttpStatus.CREATED)
    public FishResponseDto createFish(@Valid @RequestBody FishCreateDto fishCreateDto) {
        Fish fish = fishService.create(fishCreateDto);
        return fishDtoAssembler.toModel(fish);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FishResponseDto> updateFish(@PathVariable("id") Integer id,
                                                      @Valid @RequestBody FishUpdateDto fishUpdateDto) {
        Fish fish = fishService.update(fishUpdateDto, id);
        return new ResponseEntity<>(fishDtoAssembler.toModel(fish), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FishResponseDto> findById(@PathVariable("id") Integer id) {
        Fish fish = fishService.findBy(id);
        return new ResponseEntity<>(fishDtoAssembler.toModel(fish), HttpStatus.OK);
    }

}
