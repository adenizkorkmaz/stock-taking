package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import org.springframework.stereotype.Service;

@Service
public class AquariumFishCountPredicate extends BaseFilter {

    @Override
    public boolean test(Aquarium aquarium) {
        return aquarium.getFishes().size() < aquarium.getMaxNumberOfFish();
    }

    @Override
    public Integer getOrder() {
        return 1;
    }
}
