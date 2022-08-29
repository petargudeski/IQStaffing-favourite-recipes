package org.iqstaffing.assessment.models.dtos.converters;

import org.iqstaffing.assessment.models.Ingredient;
import org.iqstaffing.assessment.models.dtos.IngredientDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Service
public class IngredientToDtoConverter implements Converter<Ingredient, IngredientDto> {

    @Override
    public IngredientDto convert(Ingredient ingredient) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());

        return  ingredientDto;
    }

    public List<IngredientDto> convert(List<Ingredient> ingredients) {
        return ingredients
                .stream()
                .map(ingredient -> {
                    IngredientDto ingredientDto = new IngredientDto();
                    ingredientDto.setId(ingredient.getId());
                    ingredientDto.setName(ingredient.getName());
                    return  ingredientDto;
                })
                .collect(Collectors.toList());
    }
}
