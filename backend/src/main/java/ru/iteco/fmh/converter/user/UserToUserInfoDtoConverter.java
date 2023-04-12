package ru.iteco.fmh.converter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.UserRoleClaimRepository;
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
    UserRoleClaimRepository userRoleClaimRepository;
    UserRoleClaimToUserRoleClaimDtoConverter userRoleClaimToUserRoleClaimDtoConverter;

    @Override
    public UserInfoDto convert(@NonNull User source) {
        Set<Role> roles = source.getUserRoles();

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(source.getId());
        userInfoDto.setFirstName(source.getFirstName());
        userInfoDto.setLastName(source.getLastName());
        userInfoDto.setMiddleName(source.getMiddleName());
        userInfoDto.setAdmin(roles.stream().anyMatch(n -> n.getName().equals("ROLE_ADMINISTRATOR")));
        userInfoDto.setEmail(UserEmailDto.builder().name(source.getEmail()).isConfirmed(source.isEmailConfirmed()).build());
        userInfoDto.setRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));

        return userInfoDto;
    }
}
