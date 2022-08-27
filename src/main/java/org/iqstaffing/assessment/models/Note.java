package org.iqstaffing.assessment.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "NOTE")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String note;

    @OneToOne
    private Recipe recipe;
}
