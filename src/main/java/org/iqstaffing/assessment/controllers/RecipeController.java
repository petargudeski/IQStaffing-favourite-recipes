package org.iqstaffing.assessment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.dtos.RecipeDto;
import org.iqstaffing.assessment.services.RecipeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/recipes")
@Tag(name = "Recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ConversionService conversionService;

    public RecipeController(RecipeService recipeService, ConversionService conversionService) {
        this.recipeService = recipeService;
        this.conversionService = conversionService;
    }

    @Operation(description = "Create recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "201", description = "Recipe was successful created.")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> add(@RequestBody Recipe recipe) {

        Recipe created = recipeService.add(recipe);
        log.info("Recipe with id={} successful created.", created.getId());

        RecipeDto createdDto = conversionService.convert(created, RecipeDto.class);
        return new ResponseEntity(createdDto, HttpStatus.CREATED);
    }

    @Operation(description = "Get Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipe not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping(value = "/{recipeId}")
    public ResponseEntity<RecipeDto> get(@PathVariable(value = "recipeId") Long recipeId) {

        Recipe recipe = recipeService.getById(recipeId);
        log.info("Fetch instruction with id={}", recipeId);

        RecipeDto recipeDto = conversionService.convert(recipe, RecipeDto.class);
        return new ResponseEntity(recipeDto, HttpStatus.OK);
    }
}
