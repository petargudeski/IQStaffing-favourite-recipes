package org.iqstaffing.assessment.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "NOTE")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;

    @OneToOne
    @JsonIgnore
    private Recipe recipe;

    public Note() {}

    public Note(String note, Recipe recipe) {
        this.note = note;
        this.recipe = recipe;
    }
}
