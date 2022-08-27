package org.iqstaffing.assessment.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "INSTRUCTION")
@Data
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instruction;
}
