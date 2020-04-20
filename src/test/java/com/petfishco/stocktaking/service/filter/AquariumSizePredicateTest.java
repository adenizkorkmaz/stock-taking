package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AquariumSizePredicateTest {

    private AquariumSizePredicate aquariumSizePredicate;

    @BeforeEach
    public void init() {
        aquariumSizePredicate = new AquariumSizePredicate();
    }

    @Test
    void test_shouldReturnTrue_whenFishHasLessThenThreeFins() {
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GOLDFISH).build(), Fish.builder().build()))
                .maxNumberOfFish(3)
                .build();
        aquariumSizePredicate.setFish(Fish.builder().numberOfFins(2).build());
        assertTrue(aquariumSizePredicate.test(aquarium));
    }

    @Test
    void test_shouldReturnTrue_whenFishHasMoreThenOrEqualToThreeFinsAndAquariumHasSizeMoreThanOrEqualTo75() {
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GOLDFISH).build(), Fish.builder().build()))
                .size(80)
                .maxNumberOfFish(3)
                .build();
        aquariumSizePredicate.setFish(Fish.builder().numberOfFins(4).build());
        assertTrue(aquariumSizePredicate.test(aquarium));
    }

    @Test
    void test_shouldReturnFalse_whenFishHasMoreThenOrEqualToThreeFinsAndAquariumHasSizeLessThan75() {
        Aquarium aquarium = Aquarium.builder()
                .fishes(Arrays.asList(Fish.builder().species(Species.GOLDFISH).build(), Fish.builder().build()))
                .size(50)
                .maxNumberOfFish(3)
                .build();
        aquariumSizePredicate.setFish(Fish.builder().numberOfFins(4).build());
        assertFalse(aquariumSizePredicate.test(aquarium));
    }
}