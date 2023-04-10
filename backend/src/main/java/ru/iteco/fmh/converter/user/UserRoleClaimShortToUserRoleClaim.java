package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.RoleRepository;
import ru.iteco.fmh.dto.user.UserRoleClaimShort;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.user.UserRoleClaim;

/**
 * конвертер из {@link UserRoleClaimShort} в {@link UserRoleClaim}//
 */
@RequiredArgsConstructor
@Component
public class UserRoleClaimShortToUserRoleClaim implements Converter<UserRoleClaimShort, UserRoleClaim> {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserRoleClaim convert(@NonNull UserRoleClaimShort claimDto) {
        var claim = new UserRoleClaim();
        var role = roleRepository.findById(claimDto.getRoleId())
                .orElseThrow(() -> new NotFoundException("Роли с таким id не существует"));
        BeanUtils.copyProperties(claimDto, claim);
        claim.setRole(role);
        return claim;
    }
}
