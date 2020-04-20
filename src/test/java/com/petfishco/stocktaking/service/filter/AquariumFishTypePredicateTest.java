package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AquariumFishTypePredicateTest {
    private AquariumFishTypePredicate aquariumFishTypePredicate;

    @BeforeEach
    public void init() {
        aquariumFishTypePredicate = new AquariumFishTypePredicate();
    }

    @Test
    void test_shouldReturnFalseWhenGoldfishAndGuppiesConflict() {
        aquariumFishTypePredicate.setFish(Fish.builder().species(Species.GOLDFISH).build());
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GUPPIES).build(), Fish.builder().build()))
                .maxNumberOfFish(3)
                .build();

        assertFalse(aquariumFishTypePredicate.test(aquarium));
    }

    @Test
    void test_shouldReturnFalseWhenGoldfishAndGuppiesConflictOtherWay() {
        aquariumFishTypePredicate.setFish(Fish.builder().species(Species.GUPPIES).build());
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GOLDFISH).build(), Fish.builder().build()))
                .maxNumberOfFish(3)
                .build();

        assertFalse(aquariumFishTypePredicate.test(aquarium));
    }

    @Test
    void test_shouldReturnTrueWhenNoConflict() {
        aquariumFishTypePredicate.setFish(Fish.builder().species(Species.ANGELFISH).build());
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GOLDFISH).build(), Fish.builder().build()))
                .maxNumberOfFish(3)
                .build();

        assertTrue(aquariumFishTypePredicate.test(aquarium));
    }
}