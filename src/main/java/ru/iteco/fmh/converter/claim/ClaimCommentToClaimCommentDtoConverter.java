package ru.iteco.fmh.converter.claim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.ClaimComment;

@Component
@RequiredArgsConstructor
public class ClaimCommentToClaimCommentDtoConverter implements Converter<ClaimComment, ClaimCommentDto> {

    @Override
    public ClaimCommentDto convert(@NonNull ClaimComment claimComment) {
        ClaimCommentDto dto = new ClaimCommentDto();
        BeanUtils.copyProperties(claimComment, dto);

        Integer claimId = claimComment.getClaim() != null ? claimComment.getClaim().getId() : null;
        Integer creatorId = claimComment.getCreator() != null ? claimComment.getCreator().getId() : null;

        dto.setClaimId(claimId);
        dto.setCreatorId(creatorId);

        return dto;
    }
}
