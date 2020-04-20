package com.petfishco.stocktaking.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class FishUpdateDto {

    @NotNull
    private Integer aquariumId;
}
