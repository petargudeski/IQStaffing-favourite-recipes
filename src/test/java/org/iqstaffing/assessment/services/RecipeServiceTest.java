package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.builders.RecipeBuilder;
import org.iqstaffing.assessment.models.Recipe;
import org.iqstaffing.assessment.repositories.IngredientRepository;
import org.iqstaffing.assessment.repositories.InstructionRepository;
import org.iqstaffing.assessment.repositories.RecipeRepository;
import org.iqstaffing.assessment.servicesImpl.IngredientServiceImpl;
import org.iqstaffing.assessment.servicesImpl.InstructionServiceImpl;
import org.iqstaffing.assessment.servicesImpl.RecipeServiceImpl;
import org.iqstaffing.assessment.util.ConverterUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private InstructionRepository instructionRepository;

    private IngredientService ingredientService;

    private InstructionService instructionService;

    private RecipeServiceImpl recipeService;

    private ConverterUtil converterUtil;

    private Recipe recipe = null;

    @BeforeEach
    public void before() {
        converterUtil = new ConverterUtil();
        ingredientService = new IngredientServiceImpl(ingredientRepository);
        instructionService = new InstructionServiceImpl(instructionRepository);
        recipeService = new RecipeServiceImpl(recipeRepository, ingredientService, instructionService, converterUtil);

        recipe = new RecipeBuilder().build();
    }

    @Test
    public void add() {
        when(recipeRepository.save(any())).thenReturn(recipe);

        Recipe saved = recipeService.add(recipe);
        assertNotNull(saved);
    }

    @Test
    public void update() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipe));

        recipe.setName("New Test Name");

        Recipe updated = recipeService.update(recipe.getId(), recipe);

        assertEquals("New Test Name", updated.getName());
    }

    @Test
    public void searchByServingsAndInsructions() {
        when(recipeRepository.searchByServingsAndInsructions(any())).thenReturn(List.of(recipe));

        List<Recipe> recipes = recipeService.searchByServingsAndInsructions("Recipes that can serve 4 persons and have “potatoes” as an ingredient");

        assertTrue(!recipes.isEmpty());
    }

    @Test
    public void searchByExcludedIngredient() {
        when(recipeRepository.searchByExcludedIngredient(any())).thenReturn(List.of(recipe));

        List<Recipe> recipes = recipeService.searchByExcludedIngredient("Recipes without “salmon” as an ingredient that has “oven” in the instructions");

        assertTrue(!recipes.isEmpty());
    }

    @Test
    public void searchRecipesByCategory() {
        when(recipeRepository.searchByCategory(any())).thenReturn(List.of(recipe));

        List<Recipe> recipes = recipeService.searchRecipesByCategory("All vegetarian recipes");

        assertTrue(!recipes.isEmpty());
    }
}
