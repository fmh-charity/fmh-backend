package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link UserRoleClaimShort} в {@link UserRoleClaim}//
 */
@RequiredArgsConstructor
@Component
public class UserRoleClaimShortToUserRoleClaim implements Converter<UserRoleClaimShort, UserRoleClaim> {

    @Override
    public UserRoleClaim convert(@NonNull UserRoleClaimShort claimDto) {
        return UserRoleClaim.builder()
                .userId(claimDto.getUserId())
                .status(claimDto.getStatus())
                .build();
    }
}
