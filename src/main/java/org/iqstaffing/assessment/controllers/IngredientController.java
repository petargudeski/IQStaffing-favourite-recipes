package org.iqstaffing.assessment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.dtos.IngredientDto;
import org.iqstaffing.assessment.models.dtos.converters.IngredientToDtoConverter;
import org.iqstaffing.assessment.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/ingredients")
@Tag(name = "Recipes ingredient")
public class IngredientController {

    private final IngredientService ingredientService;
    private final ConversionService conversionService;
    private final IngredientToDtoConverter ingredientToDtoConverter;

    @Autowired
    public IngredientController(IngredientService ingredientService, ConversionService conversionService, IngredientToDtoConverter ingredientToDtoConverter) {
        this.ingredientService = ingredientService;
        this.conversionService = conversionService;
        this.ingredientToDtoConverter = ingredientToDtoConverter;
    }

    @Operation(description = "Create recipe ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "201", description = "Ingredient was successful created")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDto> add(@RequestBody Ingredient ingredient) {
        Ingredient created = ingredientService.add(ingredient);
        log.info("Ingredient successful created.");

        IngredientDto ingredientDto = conversionService.convert(created, IngredientDto.class);
        return new ResponseEntity(ingredientDto, HttpStatus.CREATED);
    }

    @Operation(description = "Patch recipe ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found in database"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @PatchMapping("/{ingredientId}")
    public ResponseEntity<?> fetch(@PathVariable(value = "ingredientId") Long ingredientId, @RequestBody Ingredient ingredient) {
        ingredientService.update(ingredientId, ingredient.getName());
        log.info("Ingredient with {} was successful updated.", ingredientId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Get ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found in database"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientDto> get(@PathVariable(value = "ingredientId") Long ingredientId) {
        Ingredient ingredient = ingredientService.getById(ingredientId);
        log.info("Fetch ingredient with id={}", ingredientId);

        IngredientDto ingredientDto = conversionService.convert(ingredient, IngredientDto.class);
        return new ResponseEntity(ingredientDto, HttpStatus.OK);
    }

    @Operation(description = "Get all ingredients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "200", description = "Request was successful")})
    @GetMapping()
    public ResponseEntity<List<IngredientDto>> getAll() {
        List<Ingredient> ingredients = ingredientService.getAll();
        log.info("Fetch all ingredients.");

        List<IngredientDto> ingredientDtos = ingredientToDtoConverter.convert(ingredients);
        return new ResponseEntity(ingredientDtos, HttpStatus.OK);
    }

    @Operation(description = "Delete ingredient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found in database"),
            @ApiResponse(responseCode = "204", description = "Ingredient deleted successfully")})
    @DeleteMapping(value = "/{ingredientId}")
    public ResponseEntity<?> delete(@PathVariable(value = "ingredientId") Long ingredientId) {
        ingredientService.delete(ingredientId);
        log.info("Deleted ingredient with id={}", ingredientId);
        return ResponseEntity.noContent().build();
    }
}
