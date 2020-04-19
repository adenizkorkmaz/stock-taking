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

    public Predicate<Aquarium> getReducedPredicate(Fish fish) {
        List<Predicate<Aquarium>> collect = getPredicateList(fish);
        return collect.stream().reduce(p -> true, Predicate::and);
    }

    public List<Predicate<Aquarium>> getPredicateList(Fish fish) {
        return filterList.stream()
                .sorted(Comparator.comparing(BaseFilter::getOrder))
                .peek(baseFilter -> baseFilter.setFish(fish))
                .collect(Collectors.toList());
    }
}
