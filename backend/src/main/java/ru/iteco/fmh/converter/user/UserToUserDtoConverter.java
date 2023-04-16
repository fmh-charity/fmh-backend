package ru.iteco.fmh.converter.user;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

/**
 * конвертер из {@link User} в {@link UserDto}//
 */
@RequiredArgsConstructor
@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(@NonNull User user) {
        Profile userProfile = user.getProfile();
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .lastName(userProfile.getLastName())
                .firstName(userProfile.getFirstName())
                .middleName(userProfile.getMiddleName())
                .email(userProfile.getEmail())
                .build();
    }
}
