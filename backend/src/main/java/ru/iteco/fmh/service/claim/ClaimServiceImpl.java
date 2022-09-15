package ru.iteco.fmh.service.claim;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.iteco.fmh.Util;
import ru.iteco.fmh.dao.repository.ClaimCommentRepository;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.pagination.PageablePogo;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.task.Status.CANCELLED;
import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final ClaimCommentRepository claimCommentRepository;
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final List<String> typesOfSort = List.of("title", "titleReverse", "status", "statusReverse", "createDate", "createDateReverse");
    private final List<String> statuses = List.of("IN_PROGRESS", "CANCELLED", "OPEN", "EXECUTED");
    String administrator = "ROLE_ADMINISTRATOR";

    @Override
    public List<ClaimDto> getAllClaims() {
        List<Claim> list = claimRepository.findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc();
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimDto> getOpenInProgressClaims() {
        List<Claim> list = claimRepository
                .findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(List.of(OPEN, IN_PROGRESS));
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ClaimDto createClaim(ClaimDto claimDto) {
        claimDto.setStatus(claimDto.getExecutorId() == null ? OPEN : IN_PROGRESS);
        Claim claim = conversionService.convert(claimDto, Claim.class);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Override
    public ClaimDto getClaim(int id) {
        Claim claim = claimRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует"));
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Transactional
    @Override
    public ClaimDto updateClaim(ClaimDto claimDto, Authentication authentication) {
        User userCreator = userRepository.findUserById(claimDto.getCreatorId());
        Util util = new Util(userRepository);
        util.checkUpdatePossibility(userCreator, authentication);
        claimDto.setStatus(claimDto.getExecutorId() == null ? OPEN : IN_PROGRESS);
        Claim claim = conversionService.convert(claimDto, Claim.class);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Transactional
    @Override
    public ClaimDto changeStatus(int claimId, Status status, Integer executorId, ClaimCommentDto claimCommentDto) {
        Claim claim = claimRepository.findById(claimId).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует"));
        if (claim.getStatus() == IN_PROGRESS && status != CANCELLED) {
            if (!claimCommentDto.getDescription().equals("")) {
                addComment(claimId, claimCommentDto);
            } else {
                throw new IllegalArgumentException("Комментарий не может быть пустым!");
            }
        }
        User executor = new User();
        if (executorId != null) {
            executor = userRepository.findById(executorId).orElseThrow(() ->
                    new IllegalArgumentException("User does not exist!"));
        }
        claim.changeStatus(status, executor);
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
    public ClaimCommentDto addComment(int claimId, ClaimCommentDto claimCommentDto) {
        ClaimComment claimComment = conversionService.convert(claimCommentDto, ClaimComment.class);
        claimComment.setClaim(claimRepository.findById(claimId).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует")));
        claimCommentRepository.save(claimComment);
        return conversionService.convert(claimComment, ClaimCommentDto.class);
    }

    @Transactional
    @Override
    public ClaimCommentDto updateClaimComment(ClaimCommentDto commentDto, Authentication authentication) {
        User userCreator = userRepository.findUserById(commentDto.getCreatorId());
        Util util = new Util(userRepository);
        util.checkUpdatePossibility(userCreator, authentication);
        ClaimComment claimComment = conversionService.convert(commentDto, ClaimComment.class);
        claimComment = claimCommentRepository.save(claimComment);
        return conversionService.convert(claimComment, ClaimCommentDto.class);
    }

    @Override
    public List<ClaimDto> getPaginationClaims(PageablePogo pageablePogo) {
        Pageable pageableList = null;

        if (typesOfSort.contains(pageablePogo.getTypeOfSort())){
            pageableList = pageablePogo.getTypeOfSort().contains("Reverse")
                ? PageRequest.of(pageablePogo.getPage(), pageablePogo.getElements(),
                    Sort.by(pageablePogo.getTypeOfSort().replace("Reverse", "")).descending())
                : PageRequest.of(pageablePogo.getPage(), pageablePogo.getElements(),
                    Sort.by(pageablePogo.getTypeOfSort()));  
        } else {
            pageableList = PageRequest.of(pageablePogo.getPage(), pageablePogo.getElements(), Sort.by("title")); 
        }

        List<Claim> list = statuses.contains(pageablePogo.getStatus()) 
            ? claimRepository.findAllByStatus(Status.valueOf(pageablePogo.getStatus()), pageableList)
            : claimRepository.findAll(pageableList).getContent();

        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }
}
