package com.petfishco.stocktaking.service.filter;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FilterChain {

    private final List<BaseFilter> filterList;

    public Predicate<Aquarium> getPredicates(Fish fish) {
        List<Predicate<Aquarium>> collect = filterList.stream()
                .sorted(Comparator.comparing(BaseFilter::getOrder))
                .peek(baseFilter -> baseFilter.setFish(fish))
                .collect(Collectors.toList());

        return collect.stream().reduce(p -> true, Predicate::and);
    }
}
