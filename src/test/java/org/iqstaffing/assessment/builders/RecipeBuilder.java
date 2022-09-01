package org.iqstaffing.assessment.builders;

import org.iqstaffing.assessment.models.*;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.models.enums.Difficulty;

import java.util.LinkedList;
import java.util.List;

public class RecipeBuilder {

    private String name = "Test Recipe";
    private Integer numberOfservings = 4;
    private Difficulty difficulty = Difficulty.MEDIUM;
    private Category category = Category.VEGETARIAN;
    private Instruction instruction = new Instruction("Test instruction");
    private List<RecipeIngredient> recipeIngredients = new LinkedList<>();

    public Recipe build() {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setNumberOfServings(numberOfservings);
        recipe.setDifficulty(difficulty);
        recipe.setCategory(category);
        recipe.setNote(new Note("Test Note", recipe));
        recipe.setInstruction(instruction);
        RecipeIngredient rg1 = new RecipeIngredient();
        rg1.setIngredient(new Ingredient("Test Ingredient"));
        rg1.setRecipe(recipe);
        RecipeIngredient rg2 = new RecipeIngredient();
        rg2.setIngredient(new Ingredient("Test 2 Ingredient"));
        rg2.setRecipe(recipe);
        recipeIngredients.add(rg1);
        recipeIngredients.add(rg2);
        recipe.setRecipeIngredients(recipeIngredients);

        return recipe;
    }
}
