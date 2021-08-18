package ru.iteco.fmh.converter.claim.fromClaim;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;

import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;


public class ClaimToClaimDtoConverter implements Converter<Claim, ClaimDto> {
    private final IUserToUserDtoConverter userToUserDtoConverter;

    public ClaimToClaimDtoConverter(IUserToUserDtoConverter userToUserDtoConverter) {
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @Override
    public ClaimDto convert(Claim claim) {
        ClaimDto dto = new ClaimDto();
        if (claim.getExecutor() == null) {
            //делаем Mock и ставим вместо executor = null
            claim.setExecutor(getUser());
            BeanUtils.copyProperties(claim, dto);
            UserDto creator = userToUserDtoConverter.convert(claim.getCreator());
            //убираем mock и ставим null обратно
            dto.setExecutor(null);
            dto.setCreator(creator);
        } else {
            BeanUtils.copyProperties(claim, dto);
            UserDto creator = userToUserDtoConverter.convert(claim.getCreator());
            UserDto executor = userToUserDtoConverter.convert(claim.getExecutor());
            dto.setExecutor(executor);
            dto.setCreator(creator);
        }
        return dto;
    }

    public static User getUser() {
        User user = User.builder()
                .id(Integer.valueOf(2))
                .login("login")
                .password("login")
                .firstName("login")
                .lastName("login")
                .middleName("login")
                .phoneNumber("login")
                .email("login")
                .build();
        user.setShortUserName(null);
        return user;

    }

}
