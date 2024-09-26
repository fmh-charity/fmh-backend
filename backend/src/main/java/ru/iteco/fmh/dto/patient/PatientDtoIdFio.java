package ru.iteco.fmh.dto.patient;

public record PatientDtoIdFio(
        Integer id,
        String firstName,
        String middleName,
        String lastName
) {}
