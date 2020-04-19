package com.petfishco.stocktaking.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FishUpdateDto {

    @NotNull
    private Integer aquariumId;
}
