package ru.iteco.fmh.converter.claim.fromClaimCommentDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.claim.fromClaimDto.IClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

public class ClaimCommentDtoToClaimCommentConverter implements Converter<ClaimCommentDto, ClaimComment> {
    private final IUserDtoToUserConverter userDtoToUserConverter;
    private final IClaimDtoToClaimConverter claimDtoToClaimConverter;

    public ClaimCommentDtoToClaimCommentConverter(IUserDtoToUserConverter userDtoToUserConverter,
                                                  IClaimDtoToClaimConverter claimDtoToClaimConverter) {
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.claimDtoToClaimConverter = claimDtoToClaimConverter;
    }

    @Override
    public ClaimComment convert(ClaimCommentDto claimCommentDto) {
        ClaimComment claimComment = new ClaimComment();
        BeanUtils.copyProperties(claimCommentDto, claimComment);
        Claim claim = claimDtoToClaimConverter.convert(claimCommentDto.getClaim());
        User creator = userDtoToUserConverter.convert(claimCommentDto.getCreator());
        claimComment.setClaim(claim);
        claimComment.setCreator(creator);
        return claimComment;
    }
}
