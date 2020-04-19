package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.AquariumDtoAssembler;
import com.petfishco.stocktaking.model.Aquarium;
import com.petfishco.stocktaking.model.dto.AquariumResponseDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import com.petfishco.stocktaking.service.AquariumService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aquariums")
@AllArgsConstructor
@Slf4j
public class AquariumController {
    private final AquariumService aquariumService;
    private final AquariumDtoAssembler aquariumDtoAssembler;

    @ApiOperation(value = "View all aquariums", response = AquariumResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved aquariums"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<AquariumResponseDto> findAll() {
        Iterable<Aquarium> all = aquariumService.findAll();
        return aquariumDtoAssembler.toCollectionModel(all);
    }


    @ApiOperation(value = "View an aquarium and fishes in it with given id", response = AquariumResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved aquarium"),
            @ApiResponse(code = 404, message = "The aquarium you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AquariumResponseDto findById(@PathVariable("id") Integer id) {
        Aquarium aquarium = aquariumService.findBy(id);
        return aquariumDtoAssembler.toModel(aquarium);
    }
}
