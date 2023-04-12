package ru.iteco.fmh.converter.user;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimDto;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link ru.iteco.fmh.model.user.UserRoleClaim} в {@link ru.iteco.fmh.dto.user.UserRoleClaimDto}
 * //для «Управление пользователями» (Для UserInfoDto)
 */
@Component
@RequiredArgsConstructor
public class UserRoleClaimToUserRoleClaimDtoConverter implements Converter<UserRoleClaim, UserRoleClaimDto> {
    RoleRepository roleRepository;

    @Override
    public UserRoleClaimDto convert(UserRoleClaim source) {
        UserRoleClaimDto userRoleClaimDto = new UserRoleClaimDto();
        userRoleClaimDto.setId(source.getId());
        userRoleClaimDto.setCreatedAt(source.getCreatedAt());
        userRoleClaimDto.setStatus(source.getStatus());
        return userRoleClaimDto;
    }
}
