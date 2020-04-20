package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.exception.BadRequestException;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.repository.FishRepository;
import com.petfishco.stocktaking.service.filter.AquariumFishCountPredicate;
import com.petfishco.stocktaking.service.filter.AquariumFishTypePredicate;
import com.petfishco.stocktaking.service.filter.AquariumSizePredicate;
import com.petfishco.stocktaking.service.filter.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FishServiceTest {
    private FishService fishService;
    private FishRepository fishRepository;
    private AquariumService aquariumService;
    private FilterChain filterChain;

    private AquariumFishCountPredicate aquariumFishCountPredicate;
    private AquariumSizePredicate aquariumSizePredicate;
    private AquariumFishTypePredicate aquariumFishTypePredicate;

    @BeforeEach
    public void init() {
        fishRepository = Mockito.mock(FishRepository.class);
        aquariumService = Mockito.mock(AquariumService.class);
        filterChain = Mockito.mock(FilterChain.class);
        fishService = new FishService(aquariumService, fishRepository, filterChain, mock(MessageSource.class));


        aquariumFishCountPredicate = new AquariumFishCountPredicate();
        aquariumSizePredicate = new AquariumSizePredicate();
        aquariumFishTypePredicate = new AquariumFishTypePredicate();
    }

    @Test
    void createGoldFishWith3Fins_ShouldGoToAnAquariumWithSizeMoreThan75AndWithoutGuppies() {
        ArrayList<Fish> fishes1 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GOLDFISH).numberOfFins(2).build()));
        ArrayList<Fish> fishes2 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        ArrayList<Fish> fishes3 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GOLDFISH).numberOfFins(2).build()));

        Aquarium aquarium1 = Aquarium.builder().id(1).size(80).maxNumberOfFish(1).fishes(fishes1).build();
        Aquarium aquarium2 = Aquarium.builder().id(2).size(100).maxNumberOfFish(3).fishes(fishes2).build();
        Aquarium aquarium3 = Aquarium.builder().id(3).size(30).maxNumberOfFish(3).fishes(fishes2).build();
        Aquarium aquarium4 = Aquarium.builder().id(4).size(75).maxNumberOfFish(4).fishes(fishes3).build();

        when(aquariumService.findAll()).thenReturn(Arrays.asList(aquarium1, aquarium2, aquarium3, aquarium4));

        Fish fish = Fish.builder()
                .color("Red")
                .numberOfFins(3)
                .species(Species.GOLDFISH).build();

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);

        when(filterChain.getReducedPredicate(any(Fish.class)))
                .thenReturn(aquariumFishCountPredicate
                        .and(aquariumSizePredicate)
                        .and(aquariumFishTypePredicate));

        when(fishRepository.save(any(Fish.class))).thenReturn(Fish.builder().aquarium(Aquarium.builder().build()).build());


        FishCreateDto fishCreateDto = FishCreateDto.builder()
                .color("Red")
                .numberOfFins(3)
                .species(Species.GOLDFISH.toString())
                .build();

        ArgumentCaptor<Fish> fishArgumentCaptor = ArgumentCaptor.forClass(Fish.class);
        fishService.create(fishCreateDto);

        verify(fishRepository).save(fishArgumentCaptor.capture());
        assertEquals(fishArgumentCaptor.getValue().getAquarium(), aquarium4);
    }

    @Test
    void createGoldFishWith2Fins_ShouldGoToAnyAquariumWithoutGuppies() {
        ArrayList<Fish> fishes1 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GOLDFISH).numberOfFins(2).build()));
        ArrayList<Fish> fishes2 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        ArrayList<Fish> fishes3 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GOLDFISH).numberOfFins(2).build()));

        Aquarium aquarium1 = Aquarium.builder().id(1).size(80).maxNumberOfFish(1).fishes(fishes1).build();
        Aquarium aquarium2 = Aquarium.builder().id(2).size(100).maxNumberOfFish(3).fishes(fishes2).build();
        Aquarium aquarium3 = Aquarium.builder().id(3).size(30).maxNumberOfFish(3).fishes(fishes3).build();
        Aquarium aquarium4 = Aquarium.builder().id(4).size(75).maxNumberOfFish(4).fishes(fishes3).build();

        when(aquariumService.findAll()).thenReturn(Arrays.asList(aquarium1, aquarium2, aquarium3, aquarium4));

        Fish fish = Fish.builder()
                .color("Red")
                .numberOfFins(2)
                .species(Species.GOLDFISH).build();

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);

        when(filterChain.getReducedPredicate(any(Fish.class)))
                .thenReturn(aquariumFishCountPredicate
                        .and(aquariumSizePredicate)
                        .and(aquariumFishTypePredicate));

        when(fishRepository.save(any(Fish.class))).thenReturn(Fish.builder().aquarium(Aquarium.builder().build()).build());


        FishCreateDto fishCreateDto = FishCreateDto.builder()
                .color("Red")
                .numberOfFins(2)
                .species(Species.GOLDFISH.toString())
                .build();

        ArgumentCaptor<Fish> fishArgumentCaptor = ArgumentCaptor.forClass(Fish.class);
        fishService.create(fishCreateDto);

        verify(fishRepository).save(fishArgumentCaptor.capture());
        assertEquals(fishArgumentCaptor.getValue().getAquarium(), aquarium3);
    }

    @Test
    void createGoldFishWith2Fins_ShouldThrowExceptionIfNoProperAquariumFound() {
        ArrayList<Fish> fishes1 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        ArrayList<Fish> fishes2 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        ArrayList<Fish> fishes3 = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));

        Aquarium aquarium1 = Aquarium.builder().id(1).size(80).maxNumberOfFish(1).fishes(fishes1).build();
        Aquarium aquarium2 = Aquarium.builder().id(2).size(100).maxNumberOfFish(3).fishes(fishes2).build();
        Aquarium aquarium3 = Aquarium.builder().id(3).size(30).maxNumberOfFish(3).fishes(fishes3).build();
        Aquarium aquarium4 = Aquarium.builder().id(4).size(75).maxNumberOfFish(4).fishes(fishes3).build();

        when(aquariumService.findAll()).thenReturn(Arrays.asList(aquarium1, aquarium2, aquarium3, aquarium4));

        Fish fish = Fish.builder()
                .color("Red")
                .numberOfFins(2)
                .species(Species.GOLDFISH).build();

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);

        when(filterChain.getReducedPredicate(any(Fish.class)))
                .thenReturn(aquariumFishCountPredicate
                        .and(aquariumSizePredicate)
                        .and(aquariumFishTypePredicate));

        when(fishRepository.save(any(Fish.class))).thenReturn(Fish.builder().aquarium(Aquarium.builder().build()).build());


        FishCreateDto fishCreateDto = FishCreateDto.builder()
                .color("Red")
                .numberOfFins(2)
                .species(Species.GOLDFISH.toString())
                .build();

        assertThrows(BadRequestException.class, () -> fishService.create(fishCreateDto));
        verifyNoInteractions(fishRepository);
    }

    @Test
    void update_shouldReturnExistingFishWithoutUpdate_whenAquariumIdIsSame() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().aquariumId(3).build();

        Fish fish = Fish.builder().id(10).aquarium(Aquarium.builder().id(3).build()).build();
        when(fishRepository.findById(10)).thenReturn(Optional.of(fish));

        Fish update = fishService.update(fishUpdateDto, 10);
        assertEquals(3, update.getAquarium().getId());
    }

    @Test
    void update_shouldThrowException_whenAquariumIsFull() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().aquariumId(4).build();

        Fish fish = Fish.builder().id(10).aquarium(Aquarium.builder().id(3).build()).build();
        when(fishRepository.findById(10)).thenReturn(Optional.of(fish));

        ArrayList<Fish> fishes = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        Aquarium aquarium = Aquarium.builder().id(4).size(80).maxNumberOfFish(1).fishes(fishes).build();
        when(aquariumService.findBy(fishUpdateDto.getAquariumId())).thenReturn(aquarium);

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);
        when(filterChain.getPredicateList(fish)).thenReturn(
                Arrays.asList(aquariumFishCountPredicate, aquariumSizePredicate, aquariumFishTypePredicate));

        assertThrows(BadRequestException.class, () -> fishService.update(fishUpdateDto, 10));
    }

    @Test
    void update_shouldThrowException_whenFishIsGoldFishButAquariumHasGuppies() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().aquariumId(4).build();

        Fish fish = Fish.builder().species(Species.GOLDFISH).id(10).aquarium(Aquarium.builder().id(3).build()).build();
        when(fishRepository.findById(10)).thenReturn(Optional.of(fish));

        ArrayList<Fish> fishes = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        Aquarium aquarium = Aquarium.builder().id(4).size(80).maxNumberOfFish(2).fishes(fishes).build();
        when(aquariumService.findBy(fishUpdateDto.getAquariumId())).thenReturn(aquarium);

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);
        when(filterChain.getPredicateList(fish)).thenReturn(
                Arrays.asList(aquariumFishCountPredicate, aquariumSizePredicate, aquariumFishTypePredicate));

        assertThrows(BadRequestException.class, () -> fishService.update(fishUpdateDto, 10));
    }

    @Test
    void update_shouldThrowException_whenFishHasThreeFinAndAquariumHasSizeBelow75() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().aquariumId(4).build();

        Fish fish = Fish.builder().numberOfFins(4).species(Species.GOLDFISH).id(10).aquarium(Aquarium.builder().id(3).build()).build();
        when(fishRepository.findById(10)).thenReturn(Optional.of(fish));

        ArrayList<Fish> fishes = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GUPPIES).numberOfFins(2).build()));
        Aquarium aquarium = Aquarium.builder().id(4).size(50).maxNumberOfFish(2).fishes(fishes).build();
        when(aquariumService.findBy(fishUpdateDto.getAquariumId())).thenReturn(aquarium);

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);
        when(filterChain.getPredicateList(fish)).thenReturn(
                Arrays.asList(aquariumFishCountPredicate, aquariumSizePredicate, aquariumFishTypePredicate));

        assertThrows(BadRequestException.class, () -> fishService.update(fishUpdateDto, 10));
    }

    @Test
    void update_shouldUpdateFish_whenAquariumIsProper() {
        FishUpdateDto fishUpdateDto = FishUpdateDto.builder().aquariumId(4).build();

        Aquarium build = Aquarium.builder().id(3).build();
        Fish fish = Fish.builder().numberOfFins(4).species(Species.GOLDFISH).id(10).aquarium(build).build();
        build.setFishes(new ArrayList<>(Collections.singletonList(fish)));
        when(fishRepository.findById(10)).thenReturn(Optional.of(fish));

        ArrayList<Fish> fishes = new ArrayList<>(Collections.singletonList(Fish.builder().species(Species.GOLDFISH).numberOfFins(2).build()));
        Aquarium aquarium = Aquarium.builder().id(4).size(100).maxNumberOfFish(2).fishes(fishes).build();
        when(aquariumService.findBy(fishUpdateDto.getAquariumId())).thenReturn(aquarium);

        aquariumSizePredicate.setFish(fish);
        aquariumFishTypePredicate.setFish(fish);
        when(filterChain.getPredicateList(fish)).thenReturn(
                Arrays.asList(aquariumFishCountPredicate, aquariumSizePredicate, aquariumFishTypePredicate));

        when(fishRepository.save(fish)).thenReturn(fish);

        Fish update = fishService.update(fishUpdateDto, 10);

        assertEquals(4, fish.getAquarium().getId());
        assertEquals(update, fish);

        verify(fishRepository).save(fish);
    }


    @Test
    void findBy() {
        Fish build = Fish.builder().build();
        when(fishRepository.findById(3)).thenReturn(Optional.of(build));
        Fish fish = fishService.findBy(3);
        assertEquals(fish, build);
    }
}