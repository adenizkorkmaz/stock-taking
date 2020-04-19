package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.exception.BadRequestException;
import com.petfishco.stocktaking.exception.NotFoundException;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.repository.FishRepository;
import com.petfishco.stocktaking.service.filter.BaseFilter;
import com.petfishco.stocktaking.service.filter.FilterChain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class FishService {
    private final AquariumService aquariumService;
    private final FishRepository fishRepository;
    private final FilterChain filterChain;

    @Transactional
    public Fish create(@Valid FishCreateDto fishCreateDto) {
        Fish fish = Fish.convertFrom(fishCreateDto);
        Iterable<Aquarium> allAquariums = aquariumService.findAll();

        Aquarium aquarium = StreamSupport.stream(allAquariums.spliterator(), false)
                .filter(filterChain.getReducedPredicate(fish))
                .findFirst().orElseThrow(() -> new BadRequestException("error.message.noProperAquariumFound"));

        aquarium.addFish(fish);
        Fish save = fishRepository.save(fish);
        log.info("Fish created with fishId: " + save.getId() + ", aquariumId: " + save.getAquarium().getId());
        return save;
    }


    @Transactional
    public Fish update(@Valid FishUpdateDto fishUpdateDto, Integer id) {
        Fish fish = findBy(id);
        Aquarium previousAquarium = fish.getAquarium();
        if (fishUpdateDto.getAquariumId().equals(previousAquarium.getId())) {
            log.info("Fish is not updated. AquariumId is the same with existing one.");
            return fish;
        }

        Aquarium aquarium = aquariumService.findBy(fishUpdateDto.getAquariumId());

        for (Predicate<Aquarium> aquariumPredicate : filterChain.getPredicateList(fish)) {
            if (!aquariumPredicate.test(aquarium)) {
                log.error("Fish cannot be updated." + ((BaseFilter) aquariumPredicate).getErrorMessage());
                throw new BadRequestException(((BaseFilter) aquariumPredicate).getErrorMessage());
            }
        }

        previousAquarium.removeFish(fish);
        aquarium.addFish(fish);
        Fish save = fishRepository.save(fish);
        log.info("Fish updated with fishId: " + save.getId() + ", aquariumId: " + save.getAquarium().getId());
        return save;
    }


    public Fish findBy(Integer id) {
        return fishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("error.message.fishNotFound", id));
    }
}
