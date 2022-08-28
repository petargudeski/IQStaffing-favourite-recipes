package org.iqstaffing.assessment.models;

import lombok.Data;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.models.enums.Difficulty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> recipeIngredients = new LinkedList<>();

    @CreatedDate
    @Column(name = "CREATED_AT")
    private Instant created;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private Instant lastModified;

    @Version
    private Integer version;
}
