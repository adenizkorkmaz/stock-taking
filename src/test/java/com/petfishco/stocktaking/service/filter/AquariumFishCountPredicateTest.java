package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class AquariumFishCountPredicateTest {
    private AquariumFishCountPredicate aquariumFishCountPredicate;

    @BeforeEach
    public void init() {
        aquariumFishCountPredicate = new AquariumFishCountPredicate();
    }

    @Test
    void test_shouldReturnTrue_whenAquariumIsNotFull() {
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().build(), Fish.builder().build()))
                .maxNumberOfFish(3)
                .build();
        Assertions.assertTrue(aquariumFishCountPredicate.test(aquarium));
    }

    @Test
    void test_shouldReturnFalse_whenAquariumIsFull() {
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().build(), Fish.builder().build()))
                .maxNumberOfFish(2)
                .build();
        Assertions.assertFalse(aquariumFishCountPredicate.test(aquarium));
    }
}