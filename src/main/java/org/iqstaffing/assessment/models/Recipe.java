package org.iqstaffing.assessment.models;

import lombok.Data;
import org.hibernate.search.mapper.pojo.bridge.builtin.annotation.AlternativeDiscriminator;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;
import org.iqstaffing.assessment.models.enums.Category;
import org.iqstaffing.assessment.models.enums.Difficulty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Indexed
@Entity
@Table(schema = "FAVORITE_RECIPES", name = "RECIPE")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@FullTextField()
    private String name;

    //@GeneratedValue()
    @Column(name = "NUMBER_OF_SERVINGS")
    private int numberOfServings;

    private Difficulty difficulty;

    @AlternativeDiscriminator
//    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL ,fetch = FetchType.EAGER)
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
