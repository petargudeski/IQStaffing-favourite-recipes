package org.iqstaffing.assessment.servicesImpl;

import lombok.extern.slf4j.Slf4j;
import org.iqstaffing.assessment.exceptions.InstructionNotFoundException;
import org.iqstaffing.assessment.models.Instruction;
import org.iqstaffing.assessment.repositories.InstructionRepository;
import org.iqstaffing.assessment.services.InstructionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InstructionServiceImpl implements InstructionService {

    private InstructionRepository instructionRepository;

    @Autowired
    public InstructionServiceImpl(InstructionRepository instructionRepository) {
        this.instructionRepository = instructionRepository;
    }

    @Override
    public Instruction add(Instruction instruction) {
        return instructionRepository.save(instruction);
    }

    @Override
    @Transactional
    public void update(Long id, String body) {
        Instruction instruction = getById(id);
        instruction.setInstruction(body);
    }

    @Override
    public List<Instruction> getAll() {
        return instructionRepository.findAll();
    }

    @Override
    public Instruction getById(Long id) {
        Instruction instruction = instructionRepository.findById(id).orElseThrow(() -> new InstructionNotFoundException(id));
        log.info("Fetch instruction with id={}", id);
        return instruction;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        instructionRepository.delete(getById(id));
    }
}
