package ru.iteco.fmh.model.document;

public enum DocumentStatus {

    NEW("НОВЫЙ"),

    PUBLISHED("ОПУБЛИКОВАН"),

    DELETED("В АРХИВЕ");

    private final String name;

    DocumentStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}