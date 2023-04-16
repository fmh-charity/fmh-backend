package ru.iteco.fmh.converter.user;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.ProfileDtoRs;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.user.User;

/**
 * конвертер из {@link User} в {@link UserDto}//
 */
@RequiredArgsConstructor
@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    private final ProfileToProfileDtoRsConverter profileToProfileDtoRsConverter;

    @Override
    public UserDto convert(@NonNull User user) {
        ProfileDtoRs userProfileDtoRs = profileToProfileDtoRsConverter.convert(user.getProfile());
        return UserDto.builder().id(user.getId()).login(user.getLogin()).profile(userProfileDtoRs).build();
    }
}
