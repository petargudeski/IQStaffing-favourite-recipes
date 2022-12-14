package org.iqstaffing.assessment.models;

import lombok.Data;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.mapper.pojo.automaticindexing.ReindexOnUpdate;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.ValueBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import org.iqstaffing.assessment.models.bridges.EnumValueBridge;
import org.iqstaffing.assessment.models.bridges.IntegerBridge;
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

    @FullTextField()
    private String name;

    @GenericField(valueBridge = @ValueBridgeRef(type = IntegerBridge.class))
    @Column(name = "NUMBER_OF_SERVINGS")
    private Integer numberOfServings;

    private Difficulty difficulty;

    @GenericField(searchable = Searchable.YES, valueBridge = @ValueBridgeRef(type = EnumValueBridge.class))
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @IndexedEmbedded
    @IndexingDependency(reindexOnUpdate = ReindexOnUpdate.SHALLOW)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @IndexedEmbedded
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
