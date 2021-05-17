package ru.iteco.fmh.converter.user.fromUser;


import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.dto.user.UserDto;

/**
 * конвертер из {@link User} в {@link UserDto}//
 */
public class UserToUserDtoConverter implements Converter<User, UserDto>, IUserToUserDtoConverter {

    @Override
    public UserDto convert (User user){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
