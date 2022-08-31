package org.iqstaffing.assessment.models.enums;

public enum Unit {
    WHOLE("whole"),
    COUNT("count"),
    HANDFUL("handful");

    private String name;

    Unit(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
