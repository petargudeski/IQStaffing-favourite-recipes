package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Instruction;

import java.util.List;

public interface InstructionService {

    Instruction add(Instruction instruction);

    void update(Long id, String body);

    List<Instruction> getAll();

    Instruction getById(Long id);

    void delete(Long id);
}
