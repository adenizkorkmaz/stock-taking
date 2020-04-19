package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AquariumSizePredicate extends BaseFilter {

    public static final int NUMBER_OF_FINS_CHECK = 3;
    public static final int AQUARIUM_SIZE_CHECK = 75;

    public AquariumSizePredicate(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public boolean test(Aquarium aquarium) {
        if (getFish().getNumberOfFins() < NUMBER_OF_FINS_CHECK) {
            return true;
        }
        return aquarium.getSize() >= AQUARIUM_SIZE_CHECK;
    }

    @Override
    public Integer getOrder() {
        return 2;
    }

    @Override
    public String getErrorMessage() {
        return getMessageSource().getMessage("filter.message.aquariumSize", null, Locale.getDefault());
    }
}
