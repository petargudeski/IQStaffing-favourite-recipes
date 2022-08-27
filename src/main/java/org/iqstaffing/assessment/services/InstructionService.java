package org.iqstaffing.assessment.services;

import org.iqstaffing.assessment.models.Instruction;

import java.util.List;

public interface InstructionService {

    public Instruction add(Instruction instruction);

    public void update(Long id, String body);

    public List<Instruction> getAll();

    public Instruction getById(Long id);

    public void delete(Long id);
}
