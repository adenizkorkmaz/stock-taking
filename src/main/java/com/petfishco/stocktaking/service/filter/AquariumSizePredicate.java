package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import org.springframework.stereotype.Service;

@Service
public class AquariumSizePredicate extends BaseFilter {

    @Override
    public boolean test(Aquarium aquarium) {
        if (getFish().getNumberOfFins() < 3) {
            return true;
        }
        return aquarium.getSize() >= 75;
    }

    @Override
    public Integer getOrder() {
        return 2;
    }
}
