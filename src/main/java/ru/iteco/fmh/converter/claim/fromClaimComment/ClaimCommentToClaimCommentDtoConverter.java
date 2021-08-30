package ru.iteco.fmh.converter.claim.fromClaimComment;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.claim.ClaimComment;

@Component
@RequiredArgsConstructor
public class ClaimCommentToClaimCommentDtoConverter implements Converter<ClaimComment, ClaimCommentDto> {

    private final UserToUserDtoConverter userToUserDtoConverter;
    private final ClaimToClaimDtoConverter claimToClaimDtoConverter;

    @Override
    public ClaimCommentDto convert(@NonNull ClaimComment claimComment) {
        ClaimCommentDto dto = new ClaimCommentDto();
        BeanUtils.copyProperties(claimComment, dto);

        ClaimDto claim = claimComment.getClaim()!=null?
                claimToClaimDtoConverter.convert(claimComment.getClaim()) : null;
        UserDto creator = claimComment.getCreator()!=null?
                userToUserDtoConverter.convert(claimComment.getCreator()) : null;
        dto.setClaim(claim);
        dto.setCreator(creator);
        return dto;
    }
}
