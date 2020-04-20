package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.mockito.Mockito.when;

class FilterChainTest {
    private FilterChain filterChain;
    private AquariumSizePredicate aquariumSizePredicate;
    private AquariumFishCountPredicate aquariumFishCountPredicate;
    private AquariumFishTypePredicate aquariumFishTypePredicate;

    @BeforeEach
    public void init() {
        aquariumFishCountPredicate = Mockito.mock(AquariumFishCountPredicate.class);
        aquariumFishTypePredicate = Mockito.mock(AquariumFishTypePredicate.class);
        aquariumSizePredicate = Mockito.mock(AquariumSizePredicate.class);
        when(aquariumFishCountPredicate.getOrder()).thenReturn(1);
        when(aquariumSizePredicate.getOrder()).thenReturn(2);
        when(aquariumFishTypePredicate.getOrder()).thenReturn(3);
        filterChain = new FilterChain(Arrays.asList(aquariumFishCountPredicate, aquariumFishTypePredicate, aquariumSizePredicate));
    }


    @Test
    void test_getPredicateList_whenCalledShoulGetOrderedPredicateList() {
        Fish fish = Fish.builder().build();
        List<Predicate<Aquarium>> predicateList = filterChain.getPredicateList(fish);

        Assertions.assertEquals(predicateList.get(0), aquariumFishCountPredicate);
        Assertions.assertEquals(predicateList.get(1), aquariumSizePredicate);
        Assertions.assertEquals(predicateList.get(2), aquariumFishTypePredicate);
    }

}