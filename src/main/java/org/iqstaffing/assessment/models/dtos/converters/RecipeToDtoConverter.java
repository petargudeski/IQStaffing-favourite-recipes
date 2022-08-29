package org.iqstaffing.assessment.models.dtos.converters;

import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.dtos.NoteDto;
import org.iqstaffing.assessment.models.dtos.RecipeDto;
import org.iqstaffing.assessment.models.dtos.InstructionDto;
import org.iqstaffing.assessment.models.dtos.IngredientDto;
import org.iqstaffing.assessment.models.dtos.RecipeIngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Service
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
                .filter(Objects::nonNull)
                .map(recipeIngredient -> {
                    RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
                    IngredientDto ingredientDto = new IngredientDto();
                    ingredientDto.setId(recipeIngredient.getIngredient().getId());
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

    public List<RecipeDto> convert(List<Recipe> recipes) {
         return recipes
                .stream()
                .map(recipe -> {
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
                            .filter(Objects::nonNull)
                            .map(recipeIngredient -> {
                                RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
                                IngredientDto ingredientDto = new IngredientDto();
                                ingredientDto.setId(recipeIngredient.getIngredient().getId());
                                ingredientDto.setName(recipeIngredient.getIngredient().getName());
                                recipeIngredientDto.setIngredient(ingredientDto);
                                recipeIngredientDto.setQuantity(recipeIngredient.getQuantity());
                                recipeIngredientDto.setUnit(recipeIngredient.getUnit());
                                return recipeIngredientDto;
                            })
                            .collect(Collectors.toList())
                    );
                    return recipeDto;
                })
                .collect(Collectors.toList());
    }
}
