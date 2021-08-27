package ru.iteco.fmh.converter.user.fromUserDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

/**
 * конвертер из {@link UserDto} в {@link User}//
 */
@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
