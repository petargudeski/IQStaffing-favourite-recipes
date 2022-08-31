package org.iqstaffing.assessment.models.enums;

public enum Category {
    NONE("none"),
    VEGAN("vegan"),
    VEGETARIAN("vegetarian"),
    GLUTEN_FREE("gluten free");

    private String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
