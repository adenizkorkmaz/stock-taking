package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.exception.NotFoundException;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.repository.AquariumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AquariumServiceTest {
    private AquariumService aquariumService;
    private AquariumRepository aquariumRepository;

    @BeforeEach
    public void init() {
        aquariumRepository = Mockito.mock(AquariumRepository.class);
        aquariumService = new AquariumService(aquariumRepository);
    }

    @Test
    void test_findBy_shouldFindAquarium() {
        Aquarium build = Aquarium.builder().build();
        when(aquariumRepository.findById(3)).thenReturn(Optional.of(build));
        Aquarium aquarium = aquariumService.findBy(3);
        assertEquals(aquarium, build);
    }

    @Test
    void test_findBy_shouldThrowExceptionWhenNotFound() {
        when(aquariumRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> aquariumService.findBy(1));
    }

    @Test
    void test_findAll() {
        Aquarium aquarium = Aquarium.builder().build();
        when(aquariumRepository.findAll()).thenReturn(Collections.singletonList(aquarium));
        Iterable<Aquarium> all = aquariumService.findAll();

        assertEquals(all.iterator().next(), aquarium);
    }
}