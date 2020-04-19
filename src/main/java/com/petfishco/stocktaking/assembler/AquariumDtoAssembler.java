package com.petfishco.stocktaking.assembler;

import com.petfishco.stocktaking.controller.AquariumController;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AquariumDtoAssembler extends RepresentationModelAssemblerSupport<Aquarium, AquariumResponseDto> {

    private final FishDtoAssembler fishDtoAssembler;

    public AquariumDtoAssembler(FishDtoAssembler fishDtoAssembler) {
        super(AquariumController.class, AquariumResponseDto.class);
        this.fishDtoAssembler = fishDtoAssembler;
    }

    @Override
    public AquariumResponseDto toModel(Aquarium entity) {
        AquariumResponseDto aquariumResponseDto = new AquariumResponseDto();
        BeanUtils.copyProperties(entity, aquariumResponseDto);
        entity.getFishes().forEach(fish -> aquariumResponseDto.addFish(fishDtoAssembler.toModel(fish)));
        aquariumResponseDto.add(linkTo(methodOn(AquariumController.class).findById(aquariumResponseDto.getId())).withSelfRel());
        return aquariumResponseDto;
    }

    @Override
    public CollectionModel<AquariumResponseDto> toCollectionModel(Iterable<? extends Aquarium> entities) {
        CollectionModel<AquariumResponseDto> aquariumResponseDtos = super.toCollectionModel(entities);
        aquariumResponseDtos.add(linkTo(methodOn(AquariumController.class).findAll()).withSelfRel());
        return aquariumResponseDtos;
    }
}
