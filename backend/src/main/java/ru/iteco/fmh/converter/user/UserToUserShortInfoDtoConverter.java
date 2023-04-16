package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dto.user.UserEmailDto;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserToUserShortInfoDtoConverter implements Converter<User, UserShortInfoDto> {

    @Override
    public UserShortInfoDto convert(@NonNull User user) {
        Profile userProfile = user.getProfile();
        Set<Role> userRoles = user.getUserRoles();
        boolean isAdmin = Util.isAdmin(user);
        Set<String> userRoleNames = userRoles.stream().map(Role::getName).collect(Collectors.toSet());
        UserEmailDto userEmailDto = UserEmailDto.builder().name(userProfile.getEmail()).isConfirmed(userProfile.isEmailConfirmed()).build();

        return UserShortInfoDto.builder()
                .id(user.getId())
                .lastName(userProfile.getLastName())
                .firstName(userProfile.getFirstName())
                .middleName(userProfile.getMiddleName())
                .isAdmin(isAdmin)
                .email(userEmailDto)
                .roles(userRoleNames)
                .build();
    }
}
