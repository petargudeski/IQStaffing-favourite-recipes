package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Ingredient;

import java.util.List;

public interface IngredientService {

    Ingredient add(Ingredient instruction);

    void update(Long id, String body);

    List<Ingredient> getAll();

    Ingredient getById(Long id);

    void delete(Long id);
}
