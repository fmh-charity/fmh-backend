package ru.iteco.fmh.converter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserRoleClaimDto;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link ru.iteco.fmh.model.user.UserRoleClaim} в {@link ru.iteco.fmh.dto.user.UserRoleClaimDto}
 * //для «Управление пользователями» (Для UserInfoDto)
 */
@Component
@RequiredArgsConstructor
public class UserRoleClaimToUserRoleClaimDtoConverter implements Converter<UserRoleClaim, UserRoleClaimDto> {

    @Override
    public UserRoleClaimDto convert(UserRoleClaim source) {
        return UserRoleClaimDto.builder()
                .id(source.getId())
                .createdAt(source.getCreatedAt())
                .status(source.getStatus())
                .build();
    }
}
