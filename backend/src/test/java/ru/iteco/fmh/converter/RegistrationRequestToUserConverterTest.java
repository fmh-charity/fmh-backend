package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.RegistrationRequestToUserConverter;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.dto.registration.RegistrationRequest;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getRegistrationRequest;

public class RegistrationRequestToUserConverterTest {

    RegistrationRequestToUserConverter converter = new RegistrationRequestToUserConverter();

    @Test
    public void convert() {
        RegistrationRequest testEntity = getRegistrationRequest();
        User actualUser = converter.convert(testEntity);
        Profile actualProfile = actualUser.getProfile();
        assertAll(
                () -> assertEquals(testEntity.getFirstName(), actualProfile.getFirstName()),
                () -> assertEquals(testEntity.getLastName(), actualProfile.getLastName()),
                () -> assertEquals(testEntity.getMiddleName(), actualProfile.getMiddleName()),
                () -> assertEquals(testEntity.getEmail(), actualUser.getLogin()),
                () -> assertEquals(testEntity.getEmail(), actualProfile.getEmail()),
                () -> assertEquals(testEntity.getDateOfBirth(), actualProfile.getDateOfBirth())
        );
    }
}
