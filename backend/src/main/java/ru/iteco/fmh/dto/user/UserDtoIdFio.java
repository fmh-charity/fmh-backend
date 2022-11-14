package ru.iteco.fmh.dto.user;

public record UserDtoIdFio(
        Integer id,
        String firstName,
        String middleName,
        String lastName
) {}
