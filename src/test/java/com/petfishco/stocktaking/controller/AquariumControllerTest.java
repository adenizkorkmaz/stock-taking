package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.AquariumDtoAssembler;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.service.AquariumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.CollectionModel;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AquariumControllerTest {
    private AquariumController aquariumController;
    private AquariumDtoAssembler aquariumDtoAssembler;
    private AquariumService aquariumService;

    @BeforeEach
    public void init() {
        aquariumDtoAssembler = Mockito.mock(AquariumDtoAssembler.class);
        aquariumService = Mockito.mock(AquariumService.class);
        aquariumController = new AquariumController(aquariumService, aquariumDtoAssembler);
    }

    @Test
    void findAll() {
        Aquarium aquarium = Aquarium.builder().build();
        List<Aquarium> aquariumList = Collections.singletonList(aquarium);
        when(aquariumService.findAll()).thenReturn(aquariumList);
        CollectionModel<AquariumResponseDto> all = aquariumController.findAll();
        verify(aquariumDtoAssembler).toCollectionModel(aquariumList);
    }

    @Test
    void findById() {
        Aquarium aquarium = Aquarium.builder().build();
        when(aquariumService.findBy(3)).thenReturn(aquarium);
        aquariumController.findById(3);
        verify(aquariumDtoAssembler).toModel(aquarium);
    }
}