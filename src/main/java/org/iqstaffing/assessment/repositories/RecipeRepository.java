package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
