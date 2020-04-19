package com.petfishco.stocktaking.model.dto;

import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.validator.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FishRequestDto {

    @NotNull
    @ValueOfEnum(enumClass = Species.class,
            message = "Should be one of the fallowing values : " +
                    "GOLDFISH|GUPPIES|BETTAS|OSCAR|SWORDTAILS|BOLIVIAN|ANGELFISH|KILLIFISH")
    private String species;

    @NotNull
    private Integer numberOfFins;

    @NotNull
    private String color;
}


