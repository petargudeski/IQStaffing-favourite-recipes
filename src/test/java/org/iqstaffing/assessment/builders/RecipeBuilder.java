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

    private List<RecipeIngredient> recipeIngredients = new LinkedList<>();

    public Recipe build() {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setNumberOfServings(numberOfservings);
        recipe.setDifficulty(difficulty);
        recipe.setCategory(category);
        Note note = buildNote();
        recipe.setNote(note);
        Instruction instruction = buildInstruction();
        recipe.setInstruction(instruction);
        RecipeIngredient rg1 = new RecipeIngredient();
        Ingredient ing1 = buildIngredient();
        rg1.setIngredient(ing1);
        rg1.setRecipe(recipe);
        recipeIngredients.add(rg1);
        recipe.setRecipeIngredients(recipeIngredients);

        return recipe;
    }

    public Note buildNote() {
        Note note = new Note();
        note.setNote("Test note");
        return note;
    }

    public Instruction buildInstruction() {
        Instruction instruction = new Instruction();
        instruction.setInstruction("oven");
        return instruction;
    }

    public Ingredient buildIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test ingredient patatos");
        return ingredient;
    }

}
