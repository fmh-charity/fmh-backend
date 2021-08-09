package ru.iteco.fmh.converter.claim.fromClaim;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.model.claim.Claim;

public class ClaimToClaimShortInfoDtoConverter implements Converter<Claim, ClaimShortInfoDto> {
    @Override
    public ClaimShortInfoDto convert(Claim claim) {
        ClaimShortInfoDto dto = new ClaimShortInfoDto();
        BeanUtils.copyProperties(claim, dto);
        dto.setShortExecutorName(claim.getExecutor().getShortUserName());
        return dto;
    }
}
