package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
