package org.iqstaffing.assessment.exceptions;

public class IngredientNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IngredientNotFoundException(Long id) {
        super(String.format("Unknown Ingredient with id=" + id));
    }
}