package ru.iteco.cucumber.model;

public record UserDtoIdFio(
        Integer id,
        String firstName,
        String middleName,
        String lastName
) {}
