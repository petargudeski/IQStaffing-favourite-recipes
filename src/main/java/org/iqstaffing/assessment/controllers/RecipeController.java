package org.iqstaffing.assessment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.dtos.RecipeDto;
import org.iqstaffing.assessment.models.dtos.SearchRequestDTO;
import org.iqstaffing.assessment.models.dtos.converters.RecipeToDtoConverter;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.services.RecipeService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/recipes")
@Tag(name = "Recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final ConversionService conversionService;
    private final RecipeToDtoConverter recipeToDtoConverter;

    public RecipeController(RecipeService recipeService, ConversionService conversionService, RecipeToDtoConverter recipeToDtoConverter) {
        this.recipeService = recipeService;
        this.conversionService = conversionService;
        this.recipeToDtoConverter = recipeToDtoConverter;
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

    @Operation(description = "Update recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipe not found in database."),
            @ApiResponse(responseCode = "200", description = "Recipe was successful created.")})
    @PutMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> update(@RequestBody Recipe recipe,
                                            @PathVariable(value = "recipeId") Long recipeId) {
        Recipe updated = recipeService.update(recipeId, recipe);
        log.info("Recipe with id={} successful updated.", updated.getId());

        RecipeDto updatedDto = conversionService.convert(updated, RecipeDto.class);
        return new ResponseEntity(updatedDto, HttpStatus.OK);
    }

    @Operation(description = "Add ingredients to recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipe not found in database."),
            @ApiResponse(responseCode = "201", description = "Recipe was successful created.")})
    @PostMapping(value = "/{recipeId}/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> addIngredient(@RequestBody List<Ingredient> ingredients,
                                                   @PathVariable(value = "recipeId") Long recipeId,
                                                   @RequestParam(value = "quantity") int quantity,
                                                   @RequestParam(value = "unit") String unit) {
        Recipe recipe = recipeService.addIngredient(recipeId, ingredients, quantity, unit);

        RecipeDto recipeDto = conversionService.convert(recipe, RecipeDto.class);
        return new ResponseEntity(recipeDto, HttpStatus.CREATED);
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

    @Operation(description = "Get Recipes by Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping(value = "/category")
    public ResponseEntity<List<RecipeDto>> getByCategory(
            @RequestParam(value = "category", defaultValue = "VEGETARIAN", required = false) Category category) {

        List<Recipe> recipes = recipeService.getAllByCategory(category);
        log.info("Selected all recipes from {} category.", category);

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by number of servings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping(value = "/servings")
    public ResponseEntity<List<RecipeDto>> getByNumberOfServings(
            @RequestParam(value = "servings", defaultValue = "1", required = false) int servings) {

        List<Recipe> recipes = recipeService.getAllByNumberOfServings(servings);
        log.info("Selected all recipes with {} number of servings.", servings);

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by Specific Ingredients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping(value = "/ingredients")
    public ResponseEntity<List<RecipeDto>> getByIngredients(
            @RequestParam(value = "ingredients", defaultValue = "", required = false) List<String> ingredients,
            @RequestParam(value = "included", defaultValue = "true", required = false) boolean isIncluded) {

        List<Recipe> recipes = recipeService.getAllRecipesByIngredients(ingredients, isIncluded);
        if (isIncluded) {
            log.info("Fetch recipes with ingredients {}", ingredients);
        } else {
            log.info("Fetch recipes without ingredients {}", ingredients);
        }

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by Specific text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> search(SearchRequestDTO searchRequestDTO) {

        log.info("Request for recipe search received with data : " + searchRequestDTO);

        List<Recipe> recipes = recipeService.searchRecipes(searchRequestDTO.getText(), searchRequestDTO.getFields(), searchRequestDTO.getLimit());

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by Specific text group by category ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping("/search/category")
    public ResponseEntity<List<RecipeDto>> searchByCategory(SearchRequestDTO searchRequestDTO) {

        log.info("Request for recipe search received with data : " + searchRequestDTO);

        List<Recipe> recipes = recipeService.searchRecipesByCategory(searchRequestDTO.getText());

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by Specific text group by number of servings ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping("/search/servings")
    public ResponseEntity<List<RecipeDto>> searchByNumberOfServings(SearchRequestDTO searchRequestDTO) {

        log.info("Request for recipe search received with data : " + searchRequestDTO);

        List<Recipe> recipes = recipeService.searchByServingsAndInsructions(searchRequestDTO.getText());

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Get Recipes by Specific text group by number of servings ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs."),
            @ApiResponse(responseCode = "404", description = "Recipes not found in database."),
            @ApiResponse(responseCode = "200", description = "Request was successful.")})
    @GetMapping("/search/excluded/ingredients")
    public ResponseEntity<List<RecipeDto>> searchByExcludedIngredients(SearchRequestDTO searchRequestDTO) {

        log.info("Request for recipe search received with data : " + searchRequestDTO);

        List<Recipe> recipes = recipeService.searchByExcludedIngredient(searchRequestDTO.getText());

        List<RecipeDto> recipesDto = recipeToDtoConverter.convert(recipes);
        return new ResponseEntity(recipesDto, HttpStatus.OK);
    }

    @Operation(description = "Delete recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Something got wrong, check the logs"),
            @ApiResponse(responseCode = "404", description = "Recipe not found in database"),
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully")})
    @DeleteMapping(value = "/{recipeId}")
    public ResponseEntity<?> delete(@PathVariable(value = "recipeId") Long recipeId) {
        recipeService.delete(recipeId);
        log.info("Deleted recipe with id={}", recipeId);
        return ResponseEntity.noContent().build();
    }
}
