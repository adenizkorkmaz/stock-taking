package com.petfishco.stocktaking.assembler;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.AquariumSizeType;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.GlassType;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.CollectionModel;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AquariumDtoAssemblerTest {

    private FishDtoAssembler fishDtoAssembler = Mockito.mock(FishDtoAssembler.class);
    private Aquarium aquarium;
    private Fish fish;

    @BeforeEach
    public void init() {
        fish = Fish.builder().build();
        aquarium = Aquarium.builder()
                .size(75)
                .maxNumberOfFish(4)
                .glassType(GlassType.GLASS)
                .fishes(Collections.singletonList(fish))
                .id(1)
                .build();
    }

    @Test
    void toModelWithLiter() {
        AquariumSizeType aquariumSizeType = AquariumSizeType.LITER;
        AquariumDtoAssembler aquariumDtoAssembler = new AquariumDtoAssembler(fishDtoAssembler, aquariumSizeType);

        FishResponseDto fishResponseDto = FishResponseDto.builder().build();
        when(fishDtoAssembler.toModel(fish)).thenReturn(fishResponseDto);

        AquariumResponseDto aquariumResponseDto = aquariumDtoAssembler.toModel(aquarium);

        makeAssertions(aquariumResponseDto);
        assertEquals(75, aquariumResponseDto.getSize());
        assertEquals(aquariumResponseDto.getAquariumSizeType(), AquariumSizeType.LITER);
        assertEquals(aquariumResponseDto.getFishList().get(0), fishResponseDto);

    }

    @Test
    void toModelWithGallon() {
        AquariumSizeType aquariumSizeType = AquariumSizeType.GALLON;
        AquariumDtoAssembler aquariumDtoAssembler = new AquariumDtoAssembler(fishDtoAssembler, aquariumSizeType);

        FishResponseDto fishResponseDto = FishResponseDto.builder().build();
        when(fishDtoAssembler.toModel(fish)).thenReturn(fishResponseDto);

        AquariumResponseDto aquariumResponseDto = aquariumDtoAssembler.toModel(aquarium);

        makeAssertions(aquariumResponseDto);
        assertEquals(19.8, aquariumResponseDto.getSize());
        assertEquals(aquariumResponseDto.getAquariumSizeType(), AquariumSizeType.GALLON);
        assertEquals(aquariumResponseDto.getFishList().get(0), fishResponseDto);
    }

    @Test
    void toCollectionModelWithLiter() {
        AquariumSizeType aquariumSizeType = AquariumSizeType.LITER;
        AquariumDtoAssembler aquariumDtoAssembler = new AquariumDtoAssembler(fishDtoAssembler, aquariumSizeType);

        CollectionModel<AquariumResponseDto> aquariumResponseDtos = aquariumDtoAssembler.toCollectionModel(Arrays.asList(aquarium));

        assertEquals("</aquariums>;rel=\"self\"", aquariumResponseDtos.getLink("self").get().toString());
        AquariumResponseDto next = aquariumResponseDtos.getContent().iterator().next();
        makeAssertions(next);

    }

    private void makeAssertions(AquariumResponseDto aquariumResponseDto) {
        assertEquals(1, aquariumResponseDto.getId());
        assertEquals(aquariumResponseDto.getGlassType(), GlassType.GLASS);
        assertEquals("</aquariums/1>;rel=\"self\"", aquariumResponseDto.getLink("self").get().toString());
    }
}