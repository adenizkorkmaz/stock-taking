package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import lombok.Data;
import org.springframework.context.MessageSource;

import java.util.function.Predicate;

@Data
public abstract class BaseFilter implements Predicate<Aquarium> {
    private final MessageSource messageSource;

    private Fish fish;

    public abstract Integer getOrder();

    public abstract String getErrorMessage();
}
