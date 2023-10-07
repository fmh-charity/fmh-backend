package ru.iteco.fmh.converter.role;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.role.RoleDto;
import ru.iteco.fmh.model.user.Role;

@Component
@RequiredArgsConstructor
public class RoleToRoleDtoConverter implements Converter<Role, RoleDto> {
    @Override
    public RoleDto convert(Role source) {
        return RoleDto.builder().id(source.getId()).name(source.getName()).build();
    }
}
