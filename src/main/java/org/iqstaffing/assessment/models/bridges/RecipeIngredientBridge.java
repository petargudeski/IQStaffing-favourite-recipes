package org.iqstaffing.assessment.models.bridges;

import org.hibernate.search.mapper.pojo.bridge.IdentifierBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.IdentifierBridgeFromDocumentIdentifierContext;
import org.hibernate.search.mapper.pojo.bridge.runtime.IdentifierBridgeToDocumentIdentifierContext;
import org.iqstaffing.assessment.models.RecipeIngredientKey;

public class RecipeIngredientBridge implements IdentifierBridge<RecipeIngredientKey> {


    @Override
    public String toDocumentIdentifier(RecipeIngredientKey recipeIngredient, IdentifierBridgeToDocumentIdentifierContext identifierBridgeToDocumentIdentifierContext) {
        return recipeIngredient.getRecipeId() + "/" + recipeIngredient.getIngredientId();
    }

    @Override
    public RecipeIngredientKey fromDocumentIdentifier(String documentIdentifier, IdentifierBridgeFromDocumentIdentifierContext identifierBridgeFromDocumentIdentifierContext) {
        String[] split = documentIdentifier.split( "/" );
        return new RecipeIngredientKey();
    }
}