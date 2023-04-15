package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserRoleClaimFull;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link UserRoleClaim} в {@link UserRoleClaimFull}//
 */
@RequiredArgsConstructor
@Component
public class UserRoleClaimToUserRoleClaimFull implements Converter<UserRoleClaim, UserRoleClaimFull> {

    @Override
    public UserRoleClaimFull convert(@NonNull UserRoleClaim claimEntity) {
        var claimDto = new UserRoleClaimFull();
        BeanUtils.copyProperties(claimEntity, claimDto);
        claimDto.setRoleId(claimEntity.getRole().getId());
        return claimDto;
    }
}
