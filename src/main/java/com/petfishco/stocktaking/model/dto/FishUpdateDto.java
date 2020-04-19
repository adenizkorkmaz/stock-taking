package com.petfishco.stocktaking.model.dto;

import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.validator.ValueOfEnum;
import lombok.Data;

import java.util.Objects;
import java.util.stream.Stream;

@Data
public class FishUpdateDto {

    @ValueOfEnum(enumClass = Species.class,
            message = "Should be one of the fallowing values : " +
                    "GOLDFISH|GUPPIES|BETTAS|OSCAR|SWORDTAILS|BOLIVIAN|ANGELFISH|KILLIFISH")
    private String species;

    private Integer numberOfFins;

    private String color;

    private Integer aquariumId;

    public boolean isAllFieldsNull() {
        return Stream.of(aquariumId, species, numberOfFins, color).allMatch(Objects::isNull);
    }

}
