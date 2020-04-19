package com.petfishco.stocktaking.model;

import com.petfishco.stocktaking.model.dto.FishCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Fish {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private Species species;

    private int numberOfFins;

    private String color;

    @ManyToOne
    @JoinColumn(name = "aquarium_id", nullable = false)
    private Aquarium aquarium;

    public static Fish convertFrom(FishCreateDto fishCreateDto) {
        Fish fish = new Fish();
        fish.setColor(fishCreateDto.getColor());
        fish.setNumberOfFins(fishCreateDto.getNumberOfFins());
        fish.setSpecies(Species.valueOf(fishCreateDto.getSpecies()));
        return fish;
    }

}
