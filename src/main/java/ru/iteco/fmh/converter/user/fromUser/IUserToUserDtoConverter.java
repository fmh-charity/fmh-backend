package ru.iteco.fmh.converter.user.fromUser;


import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

public interface IUserToUserDtoConverter {

    UserDto convert(User user);
}



