package ru.iteco.cucumber.model;


public enum DocumentStatus {
    NEW("НОВЫЙ"),

    PUBLISHED("ОПУБЛИКОВАН"),

    ARCHIVED("В АРХИВЕ");

    private final String name;

    DocumentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
