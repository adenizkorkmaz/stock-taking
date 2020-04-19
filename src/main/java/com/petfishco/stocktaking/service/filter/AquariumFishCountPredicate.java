package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AquariumFishCountPredicate extends BaseFilter {

    public AquariumFishCountPredicate(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public boolean test(Aquarium aquarium) {
        return aquarium.getFishes().size() < aquarium.getMaxNumberOfFish();
    }

    @Override
    public Integer getOrder() {
        return 1;
    }

    @Override
    public String getErrorMessage() {
        return getMessageSource().getMessage("filter.message.fishCount", null, Locale.getDefault());
    }
}
