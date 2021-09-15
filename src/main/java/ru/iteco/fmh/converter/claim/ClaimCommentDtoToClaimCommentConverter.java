package ru.iteco.fmh.converter.claim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClaimCommentDtoToClaimCommentConverter implements Converter<ClaimCommentDto, ClaimComment> {
    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;

    @Override
    public ClaimComment convert(@NonNull ClaimCommentDto claimCommentDto) {
        ClaimComment claimComment = new ClaimComment();
        BeanUtils.copyProperties(claimCommentDto, claimComment);

        Claim claim = claimCommentDto.getClaimId() != null
                ? claimRepository.findClaimById(claimCommentDto.getClaimId()) : null;
        User creator = claimCommentDto.getCreatorId() != null
                ? userRepository.findUserById(claimCommentDto.getCreatorId()) : null;

        claimComment.setCreateDate(claimCommentDto.getCreateDate() != null
                ? Instant.ofEpochMilli(claimCommentDto.getCreateDate()) : null);
        claimComment.setClaim(claim);
        claimComment.setCreator(creator);
        return claimComment;
    }
}
