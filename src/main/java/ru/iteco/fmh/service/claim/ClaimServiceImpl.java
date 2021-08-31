package ru.iteco.fmh.service.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.ClaimCommentRepository;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimCommentRequestDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimRequestDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.task.StatusE.IN_PROGRESS;
import static ru.iteco.fmh.model.task.StatusE.OPEN;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimCommentRepository claimCommentRepository;
    private final ConversionService conversionService;

    @Override
    public List<ClaimDto> getAllClaims() {
        List<Claim> list = claimRepository.findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc();
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimDto> getOpenInProgressClaims() {
        List<Claim> list = claimRepository.findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(List.of(OPEN, IN_PROGRESS));
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int createClaim(ClaimRequestDto claimDto) {
        claimDto.setStatus(claimDto.getExecutorId() == null ? OPEN : IN_PROGRESS);
        Claim claim = conversionService.convert(claimDto, Claim.class);
        return claimRepository.save(claim).getId();
    }

    @Override
    public ClaimDto getClaim(int id) {
        Claim claim = claimRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует"));
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Transactional
    @Override
    public ClaimRequestDto updateClaim(ClaimRequestDto claimDto) {
        claimDto.setStatus(claimDto.getExecutorId() == null ? OPEN : IN_PROGRESS);
        Claim claim = conversionService.convert(claimDto, Claim.class);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim,ClaimRequestDto.class);
    }

    @Transactional
    @Override
    public ClaimDto changeStatus(int claimId, StatusE status) {
        Claim claim = claimRepository.findById(claimId).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует"));
        claim.changeStatus(status);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Override
    public ClaimCommentDto getClaimComment(int claimCommentId) {
        ClaimComment claimComment = claimCommentRepository.findById(claimCommentId).orElseThrow(() ->
                new IllegalArgumentException("Такого комментария не существует"));
        return conversionService.convert(claimComment, ClaimCommentDto.class);
    }

    @Override
    public List<ClaimCommentDto> getAllClaimsComments(int claimId) {
        List<ClaimComment> list = claimCommentRepository.findAllByClaim_Id(claimId);
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int addComment(int claimId, ClaimCommentRequestDto claimCommentDto) {
        ClaimComment claimComment = conversionService.convert(claimCommentDto, ClaimComment.class);
        claimComment.setClaim(claimRepository.findById(claimId).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует")));
        return claimCommentRepository.save(claimComment).getId();
    }

    @Transactional
    @Override
    public  ClaimCommentRequestDto updateClaimComment( ClaimCommentRequestDto commentDto) {
        ClaimComment claimComment = conversionService.convert(commentDto, ClaimComment.class);
        claimComment = claimCommentRepository.save(claimComment);
        return conversionService.convert(claimComment,  ClaimCommentRequestDto.class);
    }
}
