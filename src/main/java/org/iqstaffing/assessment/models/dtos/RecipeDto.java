package org.iqstaffing.assessment.models.dtos;

import lombok.Data;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.models.enums.Difficulty;

import java.util.LinkedList;
import java.util.List;

@Data
public class RecipeDto {

    private Long id;

    private String name;

    private int numberOfServings;

    private Difficulty difficulty;

    private Category category;

    private NoteDto note;

    private InstructionDto instruction;

    private List<RecipeIngredientDto> recipeIngredient = new LinkedList<>();
}
