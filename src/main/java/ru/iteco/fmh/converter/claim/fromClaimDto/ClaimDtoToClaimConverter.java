package ru.iteco.fmh.converter.claim.fromClaimDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.Claim;

import ru.iteco.fmh.model.user.User;

public class ClaimDtoToClaimConverter implements Converter<ClaimDto, Claim> {

    private final IUserDtoToUserConverter userDtoToUserConverter;

    public ClaimDtoToClaimConverter(IUserDtoToUserConverter userDtoToUserConverter) {
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @Override
    public Claim convert(ClaimDto dto) {
        Claim claim = new Claim();
        BeanUtils.copyProperties(dto, claim);
        User executor = userDtoToUserConverter.convert(dto.getExecutor());
        User creator = userDtoToUserConverter.convert(dto.getCreator());
        claim.setExecutor(executor);
        claim.setCreator(creator);
        return claim;
    }

}
