package com.petfishco.stocktaking.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@ToString(exclude = "fishes")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Aquarium {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private double size;

    @Enumerated(value = EnumType.STRING)
    private GlassType glassType;

    @Enumerated(value = EnumType.STRING)
    private Shape shape;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aquarium")
    private List<Fish> fishes = new ArrayList<>();

    private int maxNumberOfFish;


    public void addFish(Fish fish) {
        fishes.add(fish);
        fish.setAquarium(this);
    }

    public void removeFish(Fish fish) {
        fishes.remove(fish);
        fish.setAquarium(null);
    }

}
