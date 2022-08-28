package org.iqstaffing.assessment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.services.RecipeService;
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

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(description = "Create recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "201", description = "Recipe was successful created.")})
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        Recipe created = recipeService.add(recipe);
        log.info("Recipe with id={} successful created.", created.getId());
        return new ResponseEntity(created, HttpStatus.CREATED);
    }

    @Operation(description = "Get Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipe not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping(value = "/{recipeId}")
    public ResponseEntity<Recipe> get(@PathVariable(value = "recipeId") Long recipeId) {
        Recipe recipe = recipeService.getById(recipeId);
        log.info("Fetch instruction with id={}", recipeId);
        return new ResponseEntity(recipe, HttpStatus.OK);
    }
}
