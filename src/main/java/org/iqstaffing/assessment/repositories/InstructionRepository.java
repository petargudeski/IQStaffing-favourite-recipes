package org.iqstaffing.assessment.repositories;

import org.iqstaffing.assessment.models.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructionRepository extends JpaRepository<Instruction, Long> {
}
