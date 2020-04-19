package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.repository.AquariumRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AquariumService {
    
    private final AquariumRepository aquariumRepository;

    public Aquarium findBy(Integer id) {
        return aquariumRepository.findById(id).orElseThrow(()-> new RuntimeException("Aquarium not found"));
    }

    public Iterable<Aquarium> findAll() {
        return aquariumRepository.findAll();
    }
}
