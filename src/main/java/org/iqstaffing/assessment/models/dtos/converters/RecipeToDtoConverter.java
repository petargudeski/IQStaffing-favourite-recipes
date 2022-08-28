package org.iqstaffing.assessment.models.dtos.converters;

import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.dtos.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeToDtoConverter implements Converter<Recipe, RecipeDto> {

    @Override
    public RecipeDto convert(Recipe recipe) {

        NoteDto noteDto = new NoteDto();
        noteDto.setNote(recipe.getNote().getNote());

        InstructionDto instructionDto = new InstructionDto();
        instructionDto.setInstruction(recipe.getInstruction().getInstruction());

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setNumberOfServings(recipe.getNumberOfServings());
        recipeDto.setDifficulty(recipe.getDifficulty());
        recipeDto.setCategory(recipe.getCategory());
        recipeDto.setNote(noteDto);
        recipeDto.setInstruction(instructionDto);
        recipeDto.getRecipeIngredient().addAll(recipe.getRecipeIngredients()
                .stream()
                .map(recipeIngredient -> {
                    RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
                    IngredientDto ingredientDto = new IngredientDto();
                    ingredientDto.setName(recipeIngredient.getIngredient().getName());
                    recipeIngredientDto.setIngredient(ingredientDto);
                    recipeIngredientDto.setQuantity(recipeIngredient.getQuantity());
                    recipeIngredientDto.setUnit(recipeIngredient.getUnit());
                    return recipeIngredientDto;
                })
                .collect(Collectors.toList())
        );

        return recipeDto;
    }
}
