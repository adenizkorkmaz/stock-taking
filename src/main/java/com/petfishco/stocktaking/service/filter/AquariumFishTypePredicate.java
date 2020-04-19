package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Species;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AquariumFishTypePredicate extends BaseFilter {

    public AquariumFishTypePredicate(MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public boolean test(Aquarium aquarium) {
        if (getFish().getSpecies().equals(Species.GOLDFISH)) {
            return aquarium.getFishes().stream().noneMatch(f -> f.getSpecies().equals(Species.GUPPIES));
        }

        if (getFish().getSpecies().equals(Species.GUPPIES)) {
            return aquarium.getFishes().stream().noneMatch(f -> f.getSpecies().equals(Species.GOLDFISH));
        }

        return true;
    }

    @Override
    public Integer getOrder() {
        return 3;
    }

    @Override
    public String getErrorMessage() {
        return getMessageSource().getMessage("filter.message.fishType", null, Locale.getDefault());
    }
}
