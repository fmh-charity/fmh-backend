package ru.iteco.fmh.converter.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.role.RoleDtoRs;
import ru.iteco.fmh.model.user.Role;
import org.springframework.core.convert.converter.Converter;

@Component
@RequiredArgsConstructor
public class RoleToRoleDtoRsConverter implements Converter<Role, RoleDtoRs> {
    @Override
    public RoleDtoRs convert(Role role) {
        RoleDtoRs roleDtoRs = new RoleDtoRs();
        BeanUtils.copyProperties(role, roleDtoRs);
        return roleDtoRs;
    }
}
