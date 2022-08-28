package org.iqstaffing.assessment.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.exceptions.IngredientNotFoundException;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.Instruction;
import org.iqstaffing.assessment.models.Note;
import org.iqstaffing.assessment.models.RecipeIngredient;
import org.iqstaffing.assessment.models.enums.Unit;
import org.iqstaffing.assessment.repositories.RecipeRepository;
import org.iqstaffing.assessment.services.IngredientService;
import org.iqstaffing.assessment.services.InstructionService;
import org.iqstaffing.assessment.services.RecipeService;
import org.iqstaffing.assessment.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final InstructionService instructionService;
    private final ConverterUtil converterUtil;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, IngredientService ingredientService,
                             InstructionService instructionService, ConverterUtil converterUtil) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
        this.instructionService = instructionService;
        this.converterUtil = converterUtil;
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
                .filter(Objects::nonNull)
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

    @Override
    @Transactional
    @Retryable(include = ObjectOptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(multiplier = 2.0, random = true))
    public Recipe addIngredient(Long id, List<Ingredient> ingredients, int quantity, String unit) {

        if (!(converterUtil.getEnumFromString(Unit.class, unit) instanceof Unit)) {
            throw new IllegalArgumentException(unit + " is not correct unit type");
        }

        Recipe recipe = getById(id);

        recipe.getRecipeIngredients().addAll(ingredients
                .stream()
                .filter(Objects::nonNull)
                .map(ingredientBody -> {
                    RecipeIngredient newRecipeIngredient = new RecipeIngredient();
                    if (Objects.isNull(ingredientBody.getId())) {
                        newRecipeIngredient.setIngredient(ingredientBody);
                    } else {
                        Ingredient ingredient = ingredientService.getById(ingredientBody.getId());
                        newRecipeIngredient.setIngredient(ingredient);
                    }

                    newRecipeIngredient.setRecipe(recipe);
                    newRecipeIngredient.setQuantity(quantity);
                    newRecipeIngredient.setUnit(converterUtil.getEnumFromString(Unit.class, unit));

                    log.info("Ingredient with name {} successful added to recipe with id={}.", ingredientBody.getName(), recipe.getId());

                    return newRecipeIngredient;
                })
                .collect(Collectors.toList())
        );

        return recipe;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        recipeRepository.delete(getById(id));
    }
}
