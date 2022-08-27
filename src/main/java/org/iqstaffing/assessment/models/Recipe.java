package org.iqstaffing.assessment.models;

import lombok.Data;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.models.enums.Difficulty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "RECIPE")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "NUMBER_OF_SERVINGS")
    private int numberOfServings;

    private Difficulty difficulty;

    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @ManyToOne
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @ManyToMany
    @JoinTable(
            name = "RECIPE_INGREDIENT",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients = new LinkedList<>();;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> units = new LinkedList<>();

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> quantities = new LinkedList<>();

    @CreatedDate
    @Column(name = "CREATED_AT")
    private Instant created;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private Instant lastModified;

    @Version
    private Integer version;
}
