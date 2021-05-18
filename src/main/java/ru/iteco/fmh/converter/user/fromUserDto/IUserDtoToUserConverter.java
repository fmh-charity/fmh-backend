package ru.iteco.fmh.converter.user.fromUserDto;

import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

public interface IUserDtoToUserConverter {

    User convert(UserDto userDto);
}
