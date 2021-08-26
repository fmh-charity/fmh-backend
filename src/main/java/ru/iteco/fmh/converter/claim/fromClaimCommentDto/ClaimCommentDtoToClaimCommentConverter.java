package ru.iteco.fmh.converter.claim.fromClaimCommentDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class ClaimCommentDtoToClaimCommentConverter implements Converter<ClaimCommentDto, ClaimComment> {

    private final UserDtoToUserConverter userDtoToUserConverter;
    private final ClaimDtoToClaimConverter claimDtoToClaimConverter;

    @Override
    public ClaimComment convert(@NonNull ClaimCommentDto claimCommentDto) {
        ClaimComment claimComment = new ClaimComment();
        BeanUtils.copyProperties(claimCommentDto, claimComment);
        Claim claim = claimDtoToClaimConverter.convert(claimCommentDto.getClaim());
        User creator = userDtoToUserConverter.convert(claimCommentDto.getCreator());
        claimComment.setClaim(claim);
        claimComment.setCreator(creator);
        return claimComment;
    }
}
