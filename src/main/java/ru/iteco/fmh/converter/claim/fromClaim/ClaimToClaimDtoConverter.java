package ru.iteco.fmh.converter.claim.fromClaim;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;

import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.Claim;


public class ClaimToClaimDtoConverter implements Converter<Claim, ClaimDto> {
    private final IUserToUserDtoConverter userToUserDtoConverter;
    public ClaimToClaimDtoConverter(IUserToUserDtoConverter userToUserDtoConverter) {
        this.userToUserDtoConverter = userToUserDtoConverter;
    }
    @Override
    public ClaimDto convert(Claim claim) {
        ClaimDto dto = new ClaimDto();
        BeanUtils.copyProperties(claim, dto);
        UserDto executor = userToUserDtoConverter.convert(claim.getExecutor());
        UserDto creator = userToUserDtoConverter.convert(claim.getCreator());
        dto.setExecutor(executor);
        dto.setCreator(creator);
        return dto;
    }

}
