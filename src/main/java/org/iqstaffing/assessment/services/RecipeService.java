package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.Recipe;

import java.util.List;

public interface RecipeService {

    Recipe getById(Long id);

    Recipe add(Recipe recipe);

    Recipe update(Long recipeId, Recipe recipe);

    Recipe addIngredient(Long id, List<Ingredient> body, int quantity, String unit);

    void delete(Long id);
}
