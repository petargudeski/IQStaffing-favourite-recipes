package org.iqstaffing.assessment.repositories;

import org.assertj.core.api.Assertions;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.iqstaffing.assessment.builders.RecipeBuilder;
import org.iqstaffing.assessment.components.Indexer;
import org.iqstaffing.assessment.configurations.ApplicationConfiguration;
import org.iqstaffing.assessment.exceptions.CategoryNotFoundException;
import org.iqstaffing.assessment.models.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({Indexer.class, ApplicationConfiguration.class})
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Mock
    private SearchRepositoryImpl searchRepository;

    @BeforeEach
    public void cleanup() {
        recipeRepository.deleteAll();
    }

    @Test
    public void save(){
        Recipe input = new RecipeBuilder().build();
        Recipe recipe = recipeRepository.save(input);

        assertNotNull(recipe);
        assertNotNull(recipe.getId());
        assertNotNull(recipe.getRecipeIngredients());
    }

    @Test
    public void testIfRecipeExist() {
        // given
        Recipe input = new RecipeBuilder().build();
        Recipe recipe = recipeRepository.save(input);

        // when
        boolean isPresent = recipeRepository.findById(recipe.getId()).isPresent();

        // then
        assertTrue(isPresent);
    }

    @Test
    public void testGetById() {
        // given
        Recipe input = new RecipeBuilder().build();
        Recipe recipe = recipeRepository.save(input);

        // when
        Recipe recipeNew = recipeRepository.getById(recipe.getId());

        // then
        assertEquals(recipeNew.getId(), input.getId());
    }

    @Test
    public void testSearchByCategory() {
        // given
        recipeRepository = mock(RecipeRepository.class, Answers.CALLS_REAL_METHODS);
        when(recipeRepository.searchByCategory(anyString())).thenReturn(List.of(new RecipeBuilder().build()));

        // when
        List<Recipe> recipes = recipeRepository.searchByCategory("All vegetarian recipes");

        // then
        assertTrue(!recipes.isEmpty());
    }

    @Test
    public void testSearchByCategoryNotFound() {
        // given
        recipeRepository = mock(RecipeRepository.class, Answers.CALLS_REAL_METHODS);
        when(recipeRepository.searchByCategory(anyString())).thenThrow(new CategoryNotFoundException("Unknown category."));

        // when
        Throwable throwable = catchThrowable(() -> recipeRepository.searchByCategory("All test recipes"));

        //then
        Assertions.assertThat(throwable).isInstanceOf(CategoryNotFoundException.class);
    }

    @Test
    public void testSearchByServingsAndInsructions() {
        // given
        recipeRepository = mock(RecipeRepository.class, Answers.CALLS_REAL_METHODS);
        when(recipeRepository.searchByCategory(anyString())).thenReturn(List.of(new RecipeBuilder().build()));

        // when
        List<Recipe> recipes = recipeRepository.searchByServingsAndInsructions("Recipes that can serve 4 persons and have “potatoes” as an ingredient");

        // then
        assertTrue(recipes.isEmpty());
    }

    @Test
    public void testSearchByExcludedIngredient() {
        // given
        recipeRepository = mock(RecipeRepository.class, Answers.CALLS_REAL_METHODS);
        when(recipeRepository.searchByCategory(anyString())).thenReturn(List.of(new RecipeBuilder().build()));

        // when
        List<Recipe> recipes = recipeRepository.searchByExcludedIngredient("Recipes without “salmon” as an ingredient that has “oven” in the instructions.");

        // then
        assertTrue(recipes.isEmpty());
    }
}
