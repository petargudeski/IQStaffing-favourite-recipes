package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Instruction;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends SearchRepository<Instruction, Long> {
}
