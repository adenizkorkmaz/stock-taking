package com.petfishco.stocktaking.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.petfishco.stocktaking.model.GlassType;
import com.petfishco.stocktaking.model.Shape;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AquariumResponseDto extends RepresentationModel<AquariumResponseDto> {

    private Integer id;

    private int size;

    private GlassType glassType;

    private Shape shape;

    private int maxNumberOfFish;

    private List<FishResponseDto> fishList;

    public void addFish(FishResponseDto fishResponseDto) {
        if (CollectionUtils.isEmpty(fishList)) {
            fishList = new ArrayList<>();
        }
        fishList.add(fishResponseDto);
    }
}
