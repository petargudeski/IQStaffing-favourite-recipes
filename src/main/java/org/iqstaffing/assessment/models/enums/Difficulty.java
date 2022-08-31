package org.iqstaffing.assessment.models.enums;

public enum Difficulty {
    EASY("easy"),
    MEDIUM("medium"),
    HIGH("high");

    private String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
