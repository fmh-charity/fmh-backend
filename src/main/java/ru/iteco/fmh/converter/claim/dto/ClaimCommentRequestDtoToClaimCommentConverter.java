package ru.iteco.fmh.converter.claim.dto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentRequestDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class ClaimCommentRequestDtoToClaimCommentConverter implements Converter<ClaimCommentRequestDto, ClaimComment> {
    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;

    @Override
    public ClaimComment convert(@NonNull ClaimCommentRequestDto claimCommentRequestDto) {
        ClaimComment claimComment = new ClaimComment();
        BeanUtils.copyProperties(claimCommentRequestDto, claimComment);

        Claim claim = claimCommentRequestDto.getClaimId() != null
                ? claimRepository.findClaimById(claimCommentRequestDto.getClaimId()) : null;
        User creator = claimCommentRequestDto.getCreatorId() != null
                ? userRepository.findUserById(claimCommentRequestDto.getCreatorId()) : null;
        claimComment.setClaim(claim);
        claimComment.setCreator(creator);
        return claimComment;
    }
}
