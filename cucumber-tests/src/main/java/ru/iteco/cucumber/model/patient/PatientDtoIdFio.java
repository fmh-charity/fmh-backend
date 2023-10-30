package ru.iteco.cucumber.model.patient;

public record PatientDtoIdFio(
        Integer id,
        String firstName,
        String middleName,
        String lastName
) {}
