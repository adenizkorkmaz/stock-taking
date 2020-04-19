package com.petfishco.stocktaking.assembler;

import com.petfishco.stocktaking.controller.AquariumController;
import com.petfishco.stocktaking.controller.FishController;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FishDtoAssembler extends RepresentationModelAssemblerSupport<Fish, FishResponseDto> {
    public FishDtoAssembler() {
        super(FishController.class, FishResponseDto.class);
    }

    @Override
    public FishResponseDto toModel(Fish fish) {
        FishResponseDto fishResponseDto = new FishResponseDto();
        BeanUtils.copyProperties(fish, fishResponseDto);
        fishResponseDto.setAquarium(getAquariumResponseDto(fish));
        fishResponseDto.add(linkTo(methodOn(FishController.class).findById(fishResponseDto.getId())).withSelfRel());
        return fishResponseDto;
    }

    private AquariumResponseDto getAquariumResponseDto(Fish fish) {
        AquariumResponseDto aquariumResponseDto = new AquariumResponseDto();
        BeanUtils.copyProperties(fish.getAquarium(), aquariumResponseDto);
        aquariumResponseDto.add(linkTo(methodOn(AquariumController.class).findById(aquariumResponseDto.getId())).withSelfRel());
        return aquariumResponseDto;
    }
}
