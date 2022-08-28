package org.iqstaffing.assessment.servicesImpl;

import org.iqstaffing.assessment.exceptions.IngredientNotFoundException;
import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.repositories.IngredientRepository;
import org.iqstaffing.assessment.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(id));
    }
}
