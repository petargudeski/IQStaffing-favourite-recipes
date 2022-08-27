package org.iqstaffing.assessment.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class RecipeIngredientKey  implements Serializable {

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "ingredient_id")
    private Long ingredientId;

    public  RecipeIngredientKey() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeIngredientKey)) return false;
        RecipeIngredientKey that = (RecipeIngredientKey) o;
        return getRecipeId().equals(that.getRecipeId()) && getIngredientId().equals(that.getIngredientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRecipeId(), getIngredientId());
    }
}
