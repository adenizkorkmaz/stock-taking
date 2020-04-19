package com.petfishco.stocktaking.repository;

import com.petfishco.stocktaking.model.Aquarium;
import org.springframework.data.repository.CrudRepository;

public interface AquariumRepository extends CrudRepository<Aquarium, Integer> {
}
