package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getProfileDtoRs;
import static ru.iteco.fmh.TestUtils.getUserDto;

public class UserDtoToUserConverterTest {
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    @Test
    void convert() {
        UserDto userDto = getUserDto(getProfileDtoRs());
        User user = userDtoToUserConverter.convert(userDto);
        assertAll(
                () -> assertEquals(userDto.getId(), user.getId()),
                () -> assertEquals(userDto.getLogin(), user.getLogin())
        );
    }
}
