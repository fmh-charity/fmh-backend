package ru.iteco.fmh.converter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dto.user.UserEmailDto;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;
import lombok.NonNull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * конвертер из {@link User} в {@link UserInfoDto}//для «Управление пользователями» (Для userInfoDto)
 */
@Component
@RequiredArgsConstructor
public class UserToUserInfoDtoConverter implements Converter<User, UserInfoDto> {

    @Override
    public UserInfoDto convert(@NonNull User source) {
        Set<Role> roles = source.getUserRoles();
        return UserInfoDto.builder()
                .id(source.getId())
                .firstName(source.getProfile().getFirstName())
                .lastName(source.getProfile().getLastName())
                .middleName(source.getProfile().getMiddleName())
                .isAdmin(Util.isAdmin(source))
                .email(UserEmailDto.builder().name(source.getProfile().getEmail())
                        .isConfirmed(source.getProfile().isEmailConfirmed()).build())
                .roles(roles.stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }
}
