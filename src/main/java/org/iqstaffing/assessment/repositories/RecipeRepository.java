package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.models.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategory(Category category);

    List<Recipe> findAllByNumberOfServings(int numberOfServings);

    List<Recipe> findAllByRecipeIngredients_Ingredient_nameIn(List<String> ingredients);

    List<Recipe> findAllByRecipeIngredients_Ingredient_nameNotIn(List<String> ingredients);

}
