package org.iqstaffing.assessment.exceptions;

public class InstructionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InstructionNotFoundException(Long id) {
        super(String.format("Unknown Instruction with id=" + id));
    }
}