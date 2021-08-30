package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getUserDto;

public class UserDtoToUserConverterTest {
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    @Test
    void convert() {
        UserDto userDto = getUserDto();
        User user = userDtoToUserConverter.convert(userDto);
        Assertions.assertAll(
                () -> assertEquals(userDto.getId(), user.getId()),
                () -> assertEquals(userDto.getFirstName(), user.getFirstName()),
                () -> assertEquals(userDto.getLastName(), user.getLastName()),
                () -> assertEquals(userDto.getMiddleName(), user.getMiddleName()),
                () -> assertEquals(userDto.getLogin(), user.getLogin()),
                () -> assertEquals(userDto.getPassword(), user.getPassword()),
                () -> assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber()),
                () -> assertEquals(userDto.getEmail(), user.getEmail()),
                () -> assertEquals(userDto.getShortUserName(), user.getShortUserName())
        );
    }
}
