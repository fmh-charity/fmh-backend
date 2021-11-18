package ru.iteco.fmh.converter.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserToUserShortInfoDtoConverter implements Converter<User, UserShortInfoDto> {

    @Override
    public UserShortInfoDto convert(@NonNull User user) {
        String administrator = "ROLE_ADMINISTRATOR";
        UserShortInfoDto dto = new UserShortInfoDto();
        BeanUtils.copyProperties(user, dto);

        List<Role> userRoles = user.getUserRoles();

        boolean isAdmin = userRoles.stream().anyMatch(n -> (n.getName().equals(administrator)));
        dto.setAdmin(isAdmin);
        return dto;
    }
}
