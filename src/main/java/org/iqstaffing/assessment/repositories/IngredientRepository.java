package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends SearchRepository<Ingredient, Long> {
}
