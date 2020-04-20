package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.FishDtoAssembler;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.service.FishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FishControllerTest {
    private FishController fishController;
    private FishService fishService;
    private FishDtoAssembler fishDtoAssembler;

    @BeforeEach
    public void init() {
        fishDtoAssembler = Mockito.mock(FishDtoAssembler.class);
        fishService = Mockito.mock(FishService.class);
        fishController = new FishController(fishService, fishDtoAssembler);
    }

    @Test
    void createFish() {
        FishCreateDto fishCreateDto = FishCreateDto.builder().build();
        Fish fish = Fish.builder().build();
        when(fishService.create(fishCreateDto)).thenReturn(fish);
        fishController.createFish(fishCreateDto);
        verify(fishDtoAssembler).toModel(fish);
    }

    @Test
    void updateFish() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().build();
        Fish fish = Fish.builder().build();
        when(fishService.update(fishUpdateDto, 3)).thenReturn(fish);
        fishController.updateFish(3, fishUpdateDto);
        verify(fishDtoAssembler).toModel(fish);
    }

    @Test
    void findById() {
        Fish build = Fish.builder().build();
        when(fishService.findBy(3)).thenReturn(build);
        fishController.findById(3);
        verify(fishDtoAssembler).toModel(build);
    }
}