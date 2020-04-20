package com.petfishco.stocktaking.model.dto;

import com.petfishco.stocktaking.model.Species;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FishResponseDto extends RepresentationModel<FishResponseDto> {

    private Integer id;

    private Species species;

    private int numberOfFins;

    private String color;

    private AquariumResponseDto aquarium;

}
