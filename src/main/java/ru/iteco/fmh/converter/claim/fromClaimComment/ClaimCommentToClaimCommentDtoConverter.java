package ru.iteco.fmh.converter.claim.fromClaimComment;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.claim.fromClaim.IClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;

import ru.iteco.fmh.model.task.claim.ClaimComment;

public class ClaimCommentToClaimCommentDtoConverter implements Converter<ClaimComment, ClaimCommentDto> {

    private final IUserToUserDtoConverter userToUserDtoConverter;
    private final IClaimToClaimDtoConverter claimToClaimDtoConverter;

    public ClaimCommentToClaimCommentDtoConverter(IUserToUserDtoConverter userToUserDtoConverter,
                                                  IClaimToClaimDtoConverter claimToClaimDtoConverter) {
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.claimToClaimDtoConverter = claimToClaimDtoConverter;
    }

    @Override
    public ClaimCommentDto convert(ClaimComment claimComment) {
        ClaimCommentDto dto = new ClaimCommentDto();
        BeanUtils.copyProperties(claimComment, dto);
        ClaimDto claim = claimToClaimDtoConverter.convert(claimComment.getClaim());
        UserDto creator = userToUserDtoConverter.convert(claimComment.getCreator());
        dto.setClaim(claim);
        dto.setCreator(creator);
        return dto;
    }
}
