package com.petfishco.stocktaking.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petfishco.stocktaking.model.GlassType;
import com.petfishco.stocktaking.model.Shape;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AquariumResponseDto {

    private Integer id;

    private int size;

    private GlassType glassType;

    private Shape shape;

    private int maxNumberOfFish;
}
