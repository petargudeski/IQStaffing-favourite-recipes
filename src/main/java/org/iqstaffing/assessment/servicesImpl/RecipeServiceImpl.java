package org.iqstaffing.assessment.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.exceptions.IngredientNotFoundException;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.Instruction;
import org.iqstaffing.assessment.models.Note;
import org.iqstaffing.assessment.models.RecipeIngredient;
import org.iqstaffing.assessment.models.enums.Category;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private static final List<String> SEARCHABLE_FIELDS = Arrays.asList("name", "recipeIngredients.ingredient.name", "instruction.instruction");


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

    /**
     * Returning back Recipe object per id
     * @param id recipe id
     * @return Recipe
     */
    @Override
    public Recipe getById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    /**
     * Adding new Recipe along with all nested objects.
     * @param recipe Object
     * @return saved Recipe
     */
    @Override
    public Recipe add(Recipe recipe) {

        checkRecipe(recipe);

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

    /**
     * Update Recipe object along with all nested objects.
     * @param recipeId
     * @param recipeBody
     * @return Updated Recipe object
     */
    @Override
    @Transactional
    @Retryable(include = ObjectOptimisticLockingFailureException.class, maxAttempts = 5, backoff = @Backoff(multiplier = 2.0, random = true))
    public Recipe update(Long recipeId, Recipe recipeBody) {

        checkRecipe(recipeBody);

        Recipe recipe = getById(recipeId);
        recipe.setName(recipeBody.getName());
        recipe.setNumberOfServings(recipeBody.getNumberOfServings());
        recipe.setDifficulty(recipeBody.getDifficulty());
        recipe.setCategory(recipeBody.getCategory());
        recipe.getNote().setNote(recipeBody.getNote().getNote());
        recipe.getInstruction().setInstruction(recipeBody.getInstruction().getInstruction());

        recipe.getRecipeIngredients().forEach(recipeIngredients -> {
            recipeBody.getRecipeIngredients().forEach(recipeIngredientsBody -> {
                if (recipeIngredientsBody.getId().equals(recipeIngredients.getId())) {
                    recipeIngredients.getIngredient().setName(recipeIngredientsBody.getIngredient().getName());
                    recipeIngredients.setQuantity(recipeIngredientsBody.getQuantity());
                    recipeIngredients.setUnit(recipeIngredientsBody.getUnit());
                }
            });
        });
        return recipe;
    }

    /**
     * Adding additional ingredients to Recipe
     * @param id recipe id
     * @param ingredients List of Ingredients objects
     * @param quantity count of single ingredient
     * @param unit of quentity
     * @return Recipe object with all existing and added Ingredients
     */
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

    /**
     * @param category { NONE, VEGAN, VEGETARIAN, GLUTEN_FREE }
     * @return return all recipes by chosen category
     */
    @Override
    public List<Recipe> getAllByCategory(Category category) {
        return recipeRepository.findAllByCategory(category);
    }

    /**
     * @param numberOfServings number of plates
     * @return  all recipes with this number of plates
     */
    @Override
    public List<Recipe> getAllByNumberOfServings(int numberOfServings){
        return recipeRepository.findAllByNumberOfServings(numberOfServings);
    }

    /**
     *
     * @param ingredients List of ingredients
     * @param isIncluded flag which tell us if these ingredients need to be included in the recipe or not
     * @return list of recipes with or without there ingredients
     */
    @Override
    public List<Recipe> getAllRecipesByIngredients(List<String> ingredients, boolean isIncluded) {
        List<Recipe> recipes;

        if(ingredients.isEmpty()) {
            recipes = recipeRepository.findAll();
        } else {
            if (isIncluded) {
                recipes = recipeRepository.findAllByRecipeIngredients_Ingredient_nameIn(ingredients);
            } else {
                recipes = recipeRepository.findAllByRecipeIngredients_Ingredient_nameNotIn(ingredients);
            }
        }
        return recipes;
    }

    /**
     *
     * @param text text to be searched
     * @param fields in which fields to be searched
     * @param limit how many recipes to return
     * @return full text search
     */
    public List<Recipe> searchRecipes(String text, List<String> fields, int limit) {

        List<String> fieldsToSearchBy = fields.isEmpty() ? SEARCHABLE_FIELDS : fields;

        return recipeRepository.searchBy(text, limit, fieldsToSearchBy.toArray(new String[0]));
    }

    /**
     * Remove Recipe
     * @param id Recipe id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        recipeRepository.delete(getById(id));
    }

    /**
     * Check if Recipe data is valid
     * @param recipe
     */
    private void checkRecipe(Recipe recipe) {
        if (Objects.isNull(recipe.getCategory())) {
            throw new IllegalArgumentException("Category cannot be null when creating a recipe.");
        }

        if (Objects.isNull(recipe.getDifficulty())) {
            throw new IllegalArgumentException("Difficulty cannot be null when creating a recipe.");
        }

        if (Objects.isNull(recipe.getRecipeIngredients())) {
            throw new IllegalArgumentException("Ingredients cannot be null when creating a recipe, you must have a value or remove the property.");
        }
    }
}
