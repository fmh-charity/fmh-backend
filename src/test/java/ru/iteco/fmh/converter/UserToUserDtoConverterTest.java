package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getUser;

public class UserToUserDtoConverterTest {
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        User user = getUser();
        UserDto userDto = userToUserDtoConverter.convert(user);
        Assertions.assertAll(
                () -> assertEquals(user.getId(), userDto.getId()),
                () -> assertEquals(user.getFirstName(), userDto.getFirstName()),
                () -> assertEquals(user.getLastName(), userDto.getLastName()),
                () -> assertEquals(user.getMiddleName(), userDto.getMiddleName()),
                () -> assertEquals(user.getLogin(), userDto.getLogin()),
                () -> assertEquals(user.getPassword(), userDto.getPassword()),
                () -> assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber()),
                () -> assertEquals(user.getEmail(), userDto.getEmail()),
                () -> assertEquals(user.getShortUserName(), userDto.getShortUserName())
        );
    }
}
