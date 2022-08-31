package org.iqstaffing.assessment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.IdentifierBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.DocumentId;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.iqstaffing.assessment.models.bridges.RecipeIngredientBridge;
import org.iqstaffing.assessment.models.enums.Unit;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.MapsId;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "RECIPE_INGREDIENT")
@Data
public class RecipeIngredient {

    @EmbeddedId
    @DocumentId(
            identifierBridge = @IdentifierBridgeRef(type = RecipeIngredientBridge.class)
    )
    private RecipeIngredientKey id = new RecipeIngredientKey();

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    private Unit unit;

    private int quantity;
}
