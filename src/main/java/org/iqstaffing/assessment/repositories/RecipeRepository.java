package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.enums.Category;

import java.util.List;

public interface RecipeRepository extends SearchRepository<Recipe, Long> {

    List<Recipe> findAllByCategory(Category category);

    List<Recipe> findAllByNumberOfServings(int numberOfServings);

    List<Recipe> findAllByRecipeIngredients_Ingredient_nameIn(List<String> ingredients);

    List<Recipe> findAllByRecipeIngredients_Ingredient_nameNotIn(List<String> ingredients);

}
