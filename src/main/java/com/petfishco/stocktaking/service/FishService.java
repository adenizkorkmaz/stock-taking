package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.model.dto.FishRequestDto;
import com.petfishco.stocktaking.repository.AquariumRepository;
import com.petfishco.stocktaking.repository.FishRepository;
import com.petfishco.stocktaking.service.filter.FilterChain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class FishService {
    private final AquariumRepository aquariumRepository;
    private final FishRepository fishRepository;
    private final FilterChain filterChain;

    @Transactional
    public Fish create(@Valid FishRequestDto fishRequestDto) {
        Fish fish = convertFish(fishRequestDto);
        Iterable<Aquarium> allAquariums = aquariumRepository.findAll();

        Aquarium aquarium = StreamSupport.stream(allAquariums.spliterator(), false)
                .filter(filterChain.getPredicates(fish))
                .findFirst().orElseThrow(() -> new RuntimeException("No proper aquarium found"));

        aquarium.addFish(fish);
        return fishRepository.save(fish);
    }


    private Fish convertFish(@Valid FishRequestDto fishRequestDto) {
        Fish fish = new Fish();
        fish.setColor(fishRequestDto.getColor());
        fish.setNumberOfFins(fishRequestDto.getNumberOfFins());
        fish.setSpecies(Species.valueOf(fishRequestDto.getSpecies()));
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
