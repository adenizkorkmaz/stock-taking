package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.AquariumDtoAssembler;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.GlassType;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.service.AquariumService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AquariumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AquariumDtoAssembler aquariumDtoAssembler;

    @MockBean
    private AquariumService aquariumService;

    @Test
    void findById() throws Exception {
        Aquarium aquarium = Aquarium.builder()
                .id(1)
                .size(75)
                .glassType(GlassType.GLASS)
                .build();
        when(aquariumService.findBy(1)).thenReturn(aquarium);

        AquariumResponseDto aquariumResponseDto = AquariumResponseDto.builder()
                .id(1)
                .size(75)
                .glassType(GlassType.GLASS)
                .build();
        when(aquariumDtoAssembler.toModel(aquarium)).thenReturn(aquariumResponseDto);

        mockMvc.perform(get("/aquariums/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.glassType").value("GLASS"))
                .andExpect(jsonPath("$.size").value("75.0"));
    }

    @Test
    void findAll() throws Exception {
        Aquarium aquarium = Aquarium.builder().build();
        List<Aquarium> aquariumList = Collections.singletonList(aquarium);
        when(aquariumService.findAll()).thenReturn(aquariumList);

        AquariumResponseDto aquariumResponseDto = AquariumResponseDto.builder()
                .id(1)
                .size(75)
                .glassType(GlassType.GLASS)
                .build();
        when(aquariumDtoAssembler.toCollectionModel(aquariumList))
                .thenReturn(new CollectionModel<>(Arrays.asList(aquariumResponseDto)));

        mockMvc.perform(get("/aquariums")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value("1"))
                .andExpect(jsonPath("$.content[0].glassType").value("GLASS"))
                .andExpect(jsonPath("$.content[0].size").value("75.0"));
    }
}