package com.petfishco.stocktaking.assembler;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.AquariumSizeType;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FishDtoAssemblerTest {

    private Fish fish;
    private Aquarium aquarium;

    @BeforeEach
    public void init() {
        aquarium = Aquarium.builder().size(75).id(11).build();
        fish = Fish.builder()
                .numberOfFins(3)
                .id(1)
                .aquarium(aquarium)
                .species(Species.GOLDFISH)
                .build();
    }

    @Test
    void toModelWithLiterSize() {
        AquariumSizeType aquariumSizeType = AquariumSizeType.LITER;
        FishDtoAssembler fishDtoAssembler = new FishDtoAssembler(aquariumSizeType);

        FishResponseDto fishResponseDto = fishDtoAssembler.toModel(fish);
        assertEquals(1, fishResponseDto.getId());
        assertEquals(fishResponseDto.getAquarium().getId(), aquarium.getId());
        assertEquals(3, fishResponseDto.getNumberOfFins());
        assertEquals("</fishes/1>;rel=\"self\"", fishResponseDto.getLink("self").get().toString());
        assertEquals(75, fishResponseDto.getAquarium().getSize());
        assertEquals(fishResponseDto.getAquarium().getAquariumSizeType(), AquariumSizeType.LITER);
    }

    @Test
    void toModelWithGallonSize() {
        AquariumSizeType aquariumSizeType = AquariumSizeType.GALLON;
        FishDtoAssembler fishDtoAssembler = new FishDtoAssembler(aquariumSizeType);

        FishResponseDto fishResponseDto = fishDtoAssembler.toModel(fish);
        assertEquals(1, fishResponseDto.getId());
        assertEquals(fishResponseDto.getAquarium().getId(), aquarium.getId());
        assertEquals(3, fishResponseDto.getNumberOfFins());
        assertEquals("</fishes/1>;rel=\"self\"", fishResponseDto.getLink("self").get().toString());
        assertEquals(19.8, fishResponseDto.getAquarium().getSize());
        assertEquals(fishResponseDto.getAquarium().getAquariumSizeType(), AquariumSizeType.GALLON);
    }
}