package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.enums.Category;

import java.util.List;

public interface RecipeService {

    Recipe getById(Long id);

    Recipe add(Recipe recipe);

    Recipe update(Long recipeId, Recipe recipe);

    Recipe addIngredient(Long id, List<Ingredient> body, int quantity, String unit);

    List<Recipe> getAllByCategory(Category category);

    List<Recipe> getAllByNumberOfServings(int numberOfServings);

    List<Recipe> getAllRecipesByIngredients(List<String> ingredients, boolean isIncluded);

    void delete(Long id);
}
