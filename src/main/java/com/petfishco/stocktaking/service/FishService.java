package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.repository.AquariumRepository;
import com.petfishco.stocktaking.repository.FishRepository;
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
    private final AquariumRepository aquariumRepository;
    private final FishRepository fishRepository;
    private final FilterChain filterChain;

    @Transactional
    public Fish create(@Valid FishCreateDto fishCreateDto) {
        Fish fish = convertFish(fishCreateDto);
        Iterable<Aquarium> allAquariums = aquariumRepository.findAll();

        Aquarium aquarium = StreamSupport.stream(allAquariums.spliterator(), false)
                .filter(filterChain.getReducedPredicate(fish))
                .findFirst().orElseThrow(() -> new RuntimeException("No proper aquarium found"));

        aquarium.addFish(fish);
        return fishRepository.save(fish);
    }

    public Fish update(@Valid FishUpdateDto fishUpdateDto, Integer id) {
        Fish fish = fishRepository.findById(id).orElseThrow(() -> new RuntimeException("Fish not found"));

        if (fishUpdateDto.isAllFieldsNull()) {
            return fish;
        }
        Aquarium aquarium = fish.getAquarium();
        if (fishUpdateDto.getAquariumId() != null && !aquarium.getId().equals(fishUpdateDto.getAquariumId())) {
            aquarium = aquariumRepository.findById(fishUpdateDto.getAquariumId())
                    .orElseThrow(() -> new RuntimeException("Aquarium not found"));
        }
        if (fishUpdateDto.getNumberOfFins() != 0) {
            fish.setNumberOfFins(fishUpdateDto.getNumberOfFins());
        }
        if (fish.getSpecies() != null) {
            fish.setSpecies(Species.valueOf(fishUpdateDto.getSpecies()));
        }
        if (fishUpdateDto.getColor() != null) {
            fish.setColor(fishUpdateDto.getColor());
        }

        for (Predicate<Aquarium> aquariumPredicate : filterChain.getPredicateList(fish)) {
            if (!aquariumPredicate.test(aquarium))
                throw new RuntimeException("The aquarium is not proper for this fish.");
        }
        fish.setAquarium(aquarium);
        return fishRepository.save(fish);
    }


    private Fish convertFish(@Valid FishCreateDto fishCreateDto) {
        Fish fish = new Fish();
        fish.setColor(fishCreateDto.getColor());
        fish.setNumberOfFins(fishCreateDto.getNumberOfFins());
        fish.setSpecies(Species.valueOf(fishCreateDto.getSpecies()));
        return fish;
    }


    public Fish findBy(Integer id) {
        return fishRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fish not found"));
    }

    public Iterable<Fish> findByAquarium(Integer aquariumId) {
        Iterable<Fish> byAquariumId = fishRepository.findByAquariumId(aquariumId);
        if (!byAquariumId.iterator().hasNext()) {
            throw new RuntimeException("Fish not found");
        }
        return byAquariumId;
    }

}
