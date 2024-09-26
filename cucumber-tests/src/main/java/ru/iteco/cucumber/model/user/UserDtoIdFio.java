package ru.iteco.cucumber.model.user;

public record UserDtoIdFio(
        Integer id,
        String firstName,
        String middleName,
        String lastName
) {}
