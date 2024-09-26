package ru.iteco.fmh.converter;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getProfile;
import static ru.iteco.fmh.TestUtils.getUser;

public class UserToUserDtoConverterTest {
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    @Test
    void convert() {
        User user = getUser(getProfile());
        Profile userProfile = user.getProfile();
        UserDto userDto = userToUserDtoConverter.convert(user);

        assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(userProfile.getFirstName(), userDto.getFirstName()),
                () -> assertEquals(userProfile.getLastName(), userDto.getLastName()),
                () -> assertEquals(userProfile.getMiddleName(), userDto.getMiddleName()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(userProfile.getEmail(), userDto.getEmail())
        );
    }
}
