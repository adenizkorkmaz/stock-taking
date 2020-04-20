package com.petfishco.stocktaking.model.dto;

import com.petfishco.stocktaking.model.Species;
import com.petfishco.stocktaking.validator.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishCreateDto {

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


