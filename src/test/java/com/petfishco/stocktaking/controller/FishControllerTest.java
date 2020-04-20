package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.FishDtoAssembler;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.service.FishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class FishControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FishService fishService;

    @MockBean
    private FishDtoAssembler fishDtoAssembler;

    @Test
    void createFish() throws Exception {
        Fish fish = Fish.builder().build();

        when(fishService.create(any(FishCreateDto.class))).thenReturn(fish);
        FishResponseDto fishResponseDto = FishResponseDto.builder()
                .id(1)
                .color("red")
                .species(Species.GOLDFISH)
                .numberOfFins(3)
                .build();
        when(fishDtoAssembler.toModel(fish)).thenReturn(fishResponseDto);

        mockMvc.perform(post("/fishes")
                .contentType("application/json").content("{\n" +
                        "     \"species\": \"GOLDFISH\",\n" +
                        " \t \"numberOfFins\" :3,\n" +
                        "     \"color\" : \"red\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.species").value("GOLDFISH"))
                .andExpect(jsonPath("$.numberOfFins").value("3"));
    }

    @Test
    void updateFish() throws Exception {
        Fish fish = Fish.builder().build();
        when(fishService.update(any(FishUpdateDto.class), eq(1))).thenReturn(fish);
        FishResponseDto fishResponseDto = FishResponseDto.builder()
                .id(1)
                .color("red")
                .species(Species.GOLDFISH)
                .numberOfFins(3)
                .build();
        when(fishDtoAssembler.toModel(fish)).thenReturn(fishResponseDto);


        mockMvc.perform(put("/fishes/{id}", "1")
                .contentType("application/json").content("{\"aquariumId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.species").value("GOLDFISH"))
                .andExpect(jsonPath("$.numberOfFins").value("3"));

    }

    @Test
    void findById() throws Exception {
        Fish build = Fish.builder().build();
        when(fishService.findBy(1)).thenReturn(build);

        FishResponseDto fishResponseDto = FishResponseDto.builder()
                .id(1)
                .color("red")
                .species(Species.GOLDFISH)
                .numberOfFins(3)
                .build();
        when(fishDtoAssembler.toModel(build)).thenReturn(fishResponseDto);


        mockMvc.perform(get("/fishes/{id}", "1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.color").value("red"))
                .andExpect(jsonPath("$.species").value("GOLDFISH"))
                .andExpect(jsonPath("$.numberOfFins").value("3"));
    }
}