package ru.iteco.fmh.converter.claim.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.dto.UserDtoToUserConverter;
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
    public ClaimComment convert(@NonNull ClaimCommentDto dto) {
        ClaimComment claimComment = new ClaimComment();
        BeanUtils.copyProperties(dto, claimComment);

        Claim claim = dto.getClaim() != null
                ? claimDtoToClaimConverter.convert(dto.getClaim()) : null;
        User creator = dto.getCreator() != null
                ? userDtoToUserConverter.convert(dto.getCreator()) : null;
        claimComment.setClaim(claim);
        claimComment.setCreator(creator);
        return claimComment;
    }
}
