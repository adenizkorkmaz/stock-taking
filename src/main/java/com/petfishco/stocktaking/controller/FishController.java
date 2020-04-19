package com.petfishco.stocktaking.controller;

import com.petfishco.stocktaking.assembler.FishDtoAssembler;
import com.petfishco.stocktaking.model.Fish;
import com.petfishco.stocktaking.model.dto.FishCreateDto;
import com.petfishco.stocktaking.model.dto.FishResponseDto;
import com.petfishco.stocktaking.model.dto.FishUpdateDto;
import com.petfishco.stocktaking.service.FishService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/fishes")
@AllArgsConstructor
@Slf4j
public class FishController {

    private final FishService fishService;
    private final FishDtoAssembler fishDtoAssembler;


    @ApiOperation(value = "Create new fish", response = FishResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created fish"),
            @ApiResponse(code = 400, message = "Bad request"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FishResponseDto createFish(@Valid @RequestBody FishCreateDto fishCreateDto) {
        Fish fish = fishService.create(fishCreateDto);
        return fishDtoAssembler.toModel(fish);
    }


    @ApiOperation(value = "Update given fish", response = FishResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated fish"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "The fish you were trying to reach is not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FishResponseDto updateFish(@PathVariable("id") Integer id,
                                      @Valid @RequestBody FishUpdateDto fishUpdateDto) {
        Fish fish = fishService.update(fishUpdateDto, id);
        return fishDtoAssembler.toModel(fish);
    }


    @ApiOperation(value = "View a fish with given id", response = FishResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved fish"),
            @ApiResponse(code = 404, message = "The fish you were trying to reach is not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FishResponseDto findById(@PathVariable("id") Integer id) {
        Fish fish = fishService.findBy(id);
        return fishDtoAssembler.toModel(fish);
    }

}
