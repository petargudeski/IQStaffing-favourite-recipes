package org.iqstaffing.assessment.models.dtos;

import lombok.Data;
import org.iqstaffing.assessment.models.enums.Unit;

@Data
public class RecipeIngredientDto {

    private IngredientDto ingredient;

    private Unit unit;

    private int quantity;
}
