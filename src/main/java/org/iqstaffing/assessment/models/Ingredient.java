package org.iqstaffing.assessment.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "INGREDIENT")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes = new LinkedList<>();

    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> units = new LinkedList<>();

    @OneToMany(mappedBy = "ingredient")
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
