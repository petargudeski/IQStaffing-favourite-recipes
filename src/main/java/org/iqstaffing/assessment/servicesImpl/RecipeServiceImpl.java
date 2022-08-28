package org.iqstaffing.assessment.servicesImpl;

import org.iqstaffing.assessment.exceptions.IngredientNotFoundException;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.Note;
import org.iqstaffing.assessment.models.Instruction;
import org.iqstaffing.assessment.models.RecipeIngredient;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.repositories.RecipeRepository;
import org.iqstaffing.assessment.services.IngredientService;
import org.iqstaffing.assessment.services.InstructionService;
import org.iqstaffing.assessment.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final InstructionService instructionService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientService ingredientService, InstructionService instructionService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.instructionService = instructionService;
    }

    @Override
    public Recipe getById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    @Override
    public Recipe add(Recipe recipe) {

        if (Objects.isNull(recipe.getCategory())) {
            throw new IllegalArgumentException("Category cannot be null when creating a recipe.");
        }

        if (Objects.isNull(recipe.getDifficulty())) {
            throw new IllegalArgumentException("Difficulty cannot be null when creating a recipe.");
        }

        if (Objects.isNull(recipe.getRecipeIngredients())) {
            throw new IllegalArgumentException("Ingredients cannot be null when creating a recipe, you must have a value or remove the property.");
        }

        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.getName());
        newRecipe.setNumberOfServings(recipe.getNumberOfServings());
        newRecipe.setDifficulty(recipe.getDifficulty());
        newRecipe.setCategory(recipe.getCategory());
        newRecipe.setNote(new Note(recipe.getNote().getNote(), newRecipe));

        if (Objects.isNull(recipe.getInstruction().getId())) {
            newRecipe.setInstruction(new Instruction(recipe.getInstruction().getInstruction()));
        } else {
            Instruction instruction = instructionService.getById(recipe.getInstruction().getId());
            newRecipe.setInstruction(instruction);
        }

        newRecipe.getRecipeIngredients().addAll(recipe.getRecipeIngredients()
                .stream()
                .map(recipeIngredient -> {
                    RecipeIngredient newRecipeIngredient = new RecipeIngredient();
                    newRecipeIngredient.setRecipe(newRecipe);
                    if (Objects.isNull(recipeIngredient.getIngredient().getId())) {
                        newRecipeIngredient.setIngredient(new Ingredient(recipeIngredient.getIngredient().getName()));
                    } else {
                        Ingredient ingredient = ingredientService.getById(recipeIngredient.getIngredient().getId());
                        newRecipeIngredient.setIngredient(ingredient);
                    }
                    newRecipeIngredient.setQuantity(recipeIngredient.getQuantity());
                    newRecipeIngredient.setUnit(recipeIngredient.getUnit());
                    return newRecipeIngredient;
                })
                .collect(Collectors.toList())
        );
        return recipeRepository.save(newRecipe);
    }

}
