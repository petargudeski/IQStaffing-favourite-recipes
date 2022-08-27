package org.iqstaffing.assessment.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "RECIPE_INGREDIENT")
@Data
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientKey id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private Unit unit;

    private int quantity;
}
