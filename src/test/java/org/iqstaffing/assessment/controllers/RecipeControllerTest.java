package org.iqstaffing.assessment.controllers;

import io.micrometer.core.instrument.util.IOUtils;
import org.iqstaffing.assessment.builders.RecipeBuilder;
import org.iqstaffing.assessment.models.dtos.converters.RecipeToDtoConverter;
import org.iqstaffing.assessment.repositories.RecipeRepository;
import org.iqstaffing.assessment.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToDtoConverter recipeToDtoConverter;

    @Mock
    private ConversionService conversionService;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MediaType contentType = MediaType.APPLICATION_JSON;

    private String recipesRequest;

    private String updateRequest;

    @BeforeEach
    public void given() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        recipesRequest = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("json/recipe.json"), Charset.defaultCharset());
        updateRequest = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("json/recipeUpdate.json"), Charset.defaultCharset());
    }

    @Test
    public void add() throws Exception {
        when(recipeService.add(any())).thenReturn(new RecipeBuilder().build());

        mockMvc.perform(post("/api/recipes")
                        .contentType(contentType)
                        .content(recipesRequest))
                .andExpect(status().isCreated());
    }

    @Test
    public void update() throws Exception {
        when(recipeService.update(any(), any())).thenReturn(new RecipeBuilder().build());

        mockMvc.perform(put("/api/recipes/1")
                        .contentType(contentType)
                        .content(updateRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void searchByCategory() throws Exception {
        //when(recipeService.searchRecipesByCategory(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/recipes/search/category")
                        .contentType(contentType)
                        .param("text", "All vegetarian recipes"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchByExcludedIngredient() throws Exception {
        //when(recipeService.searchByExcludedIngredient(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/recipes/search/excluded/ingredients")
                        .contentType(contentType)
                        .param("text", "Recipes without “salmon” as an ingredient that has “oven” in the instructions."))
                .andExpect(status().isOk());
    }

    @Test
    public void searchByServingsAndInsructions() throws Exception {
        //when(recipeService.searchByServingsAndInsructions(any())).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/recipes/search/category")
                        .contentType(contentType)
                        .param("text", "Recipes that can serve 4 persons and have “potatoes” as an ingredient"))
                .andExpect(status().isOk());
    }

}
