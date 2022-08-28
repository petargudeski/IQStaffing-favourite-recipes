package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Recipe;

public interface RecipeService {

    Recipe getById(Long id);

    Recipe add(Recipe recipe);
}
