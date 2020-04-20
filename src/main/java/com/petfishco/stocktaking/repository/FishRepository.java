package com.petfishco.stocktaking.repository;

import com.petfishco.stocktaking.model.Fish;
import org.springframework.data.repository.CrudRepository;

public interface FishRepository extends CrudRepository<Fish, Integer> {
}
