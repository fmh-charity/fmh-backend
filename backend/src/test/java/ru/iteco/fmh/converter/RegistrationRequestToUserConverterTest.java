package ru.iteco.fmh.converter;

import org.junit.Test;
import ru.iteco.fmh.converter.user.RegistrationRequestToUserConverter;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.dto.registration.RegistrationRequest;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getRegistrationRequest;

public class RegistrationRequestToUserConverterTest {

    RegistrationRequestToUserConverter converter = new RegistrationRequestToUserConverter();

    @Test
    public void convert() {
        RegistrationRequest testEntity = getRegistrationRequest();
        User actual = converter.convert(testEntity);
        assertAll(
                () -> assertEquals(testEntity.getFirstName(), actual.getFirstName()),
                () -> assertEquals(testEntity.getLastName(), actual.getLastName()),
                () -> assertEquals(testEntity.getMiddleName(), actual.getMiddleName()),
                () -> assertEquals(testEntity.getEmail(), actual.getLogin()),
                () -> assertEquals(testEntity.getEmail(), actual.getEmail()),
                () -> assertEquals(testEntity.getDateOfBirth(), actual.getDateOfBirth()),
                () -> assertFalse(actual.isDeleted())
        );
    }
}
