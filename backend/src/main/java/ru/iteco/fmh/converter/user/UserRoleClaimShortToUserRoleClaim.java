package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link UserRoleClaimShort} в {@link UserRoleClaim}//
 */
@RequiredArgsConstructor
@Component
public class UserRoleClaimShortToUserRoleClaim implements Converter<UserRoleClaimShort, UserRoleClaim> {

    private final RoleRepository roleRepository;

    @Override
    public UserRoleClaim convert(@NonNull UserRoleClaimShort claimDto) {
        Role role = roleRepository.findRoleById(claimDto.getRoleId());

        return UserRoleClaim.builder()
                .userId(claimDto.getUserId())
                .role(role)
                .status(claimDto.getStatus())
                .build();
    }
}
