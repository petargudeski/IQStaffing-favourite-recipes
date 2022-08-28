package org.iqstaffing.assessment.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(schema = "FAVORITE_RECIPES", name = "INSTRUCTION")
@Data
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instruction;

    public Instruction() {}

    public Instruction(String instruction) {
        this.instruction = instruction;
    }
}
