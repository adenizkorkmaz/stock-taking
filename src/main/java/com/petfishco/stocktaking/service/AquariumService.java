package com.petfishco.stocktaking.service;

import com.petfishco.stocktaking.exception.NotFoundException;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.repository.AquariumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AquariumService {

    private final AquariumRepository aquariumRepository;

    public Aquarium findBy(Integer id) {
        return aquariumRepository.findById(id).orElseThrow(() -> new NotFoundException("error.message.aquariumNotFound", id));
    }

    public Iterable<Aquarium> findAll() {
        return aquariumRepository.findAll();
    }
}
