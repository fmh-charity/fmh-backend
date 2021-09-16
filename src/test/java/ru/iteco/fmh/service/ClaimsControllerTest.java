package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.ClaimsController;
import ru.iteco.fmh.dao.repository.ClaimCommentRepository;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.iteco.fmh.TestUtils.getClaimCommentDto;
import static ru.iteco.fmh.TestUtils.getClaimDtoInProgress;
import static ru.iteco.fmh.TestUtils.getClaimDtoOpen;
import static ru.iteco.fmh.model.task.StatusE.EXECUTED;
import static ru.iteco.fmh.model.task.StatusE.IN_PROGRESS;
import static ru.iteco.fmh.model.task.StatusE.OPEN;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ClaimsControllerTest {
    @Autowired
    ClaimsController sut;
    @Autowired
    ClaimRepository claimRepository;
    @Autowired
    ClaimCommentRepository claimCommentRepository;
    @Autowired
    ConversionService conversionService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void getAllClaims() {
        List<ClaimDto> claimDtoList = sut.getAllClaims();

        assertEquals(5, claimDtoList.size());
        assertTrue(Instant.ofEpochMilli(claimDtoList.get(3).getPlanExecuteDate()).isBefore
                (Instant.ofEpochMilli(claimDtoList.get(4).getPlanExecuteDate())));
        assertTrue(Instant.ofEpochMilli(claimDtoList.get(1).getCreateDate()).isBefore
                (Instant.ofEpochMilli(claimDtoList.get(2).getCreateDate())));

    }

    @Test
    public void getAllOpenAndInProgressClaims() {
        List<ClaimDto> claimDtoList = sut.getOpenInProgressClaims();

        assertEquals(4, claimDtoList.size());
        assertTrue(Instant.ofEpochMilli(claimDtoList.get(1).getPlanExecuteDate()).isBefore
                (Instant.ofEpochMilli(claimDtoList.get(2).getPlanExecuteDate())));
        assertTrue(Instant.ofEpochMilli(claimDtoList.get(0).getCreateDate()).isBefore
                (Instant.ofEpochMilli(claimDtoList.get(1).getCreateDate())));
    }

    @Test
    public void createClaimShouldPassSuccessNull() {
        // given
        ClaimDto given = getClaimDtoOpen();
        //executor notNull
        given.setCreatorId(userRepository.findUserById(given.getCreatorId()).getId());
        ClaimDto claimDtoNullExecutor = sut.createClaim(given);

        assertNotNull(claimDtoNullExecutor.getId());
        Claim result = claimRepository.findById(claimDtoNullExecutor.getId()).get();

        assertEquals(given.getStatus(), result.getStatus());

        assertAll(
                () -> assertEquals(claimDtoNullExecutor.getDescription(), result.getDescription()),
                () -> assertEquals(claimDtoNullExecutor.getTitle(), result.getTitle()),
                () -> assertEquals(claimDtoNullExecutor.getCreatorId(), result.getCreator().getId()),
                () -> assertEquals(claimDtoNullExecutor.getExecutorId(), result.getExecutor()),
                () -> assertNull(claimDtoNullExecutor.getExecutorId()),
                () -> assertNull(claimDtoNullExecutor.getExecutorId()),
                () -> assertEquals(claimDtoNullExecutor.getCreateDate(), result.getCreateDate().toEpochMilli()),
                () -> assertEquals(claimDtoNullExecutor.getPlanExecuteDate(), result.getPlanExecuteDate().toEpochMilli())

        );

        // deleting result entity
        claimRepository.deleteById(claimDtoNullExecutor.getId());
    }

    @Test
    public void createClaimShouldPassSuccessNotNull() {
        // given
        ClaimDto given = getClaimDtoInProgress();
        //executor notNull
        given.setCreatorId(userRepository.findUserById(given.getCreatorId()).getId());
        given.setExecutorId(userRepository.findUserById(given.getExecutorId()).getId());
        ClaimDto claimDtoNotNullExecutor = sut.createClaim(given);
        assertNotNull(claimDtoNotNullExecutor.getId());
        Claim result = claimRepository.findById(claimDtoNotNullExecutor.getId()).get();
        assertEquals(given.getStatus(), result.getStatus());

        assertAll(
                () -> assertEquals(claimDtoNotNullExecutor.getDescription(), result.getDescription()),
                () -> assertEquals(claimDtoNotNullExecutor.getTitle(), result.getTitle()),
                () -> assertEquals(claimDtoNotNullExecutor.getCreatorId(), result.getCreator().getId()),
                () -> assertEquals(claimDtoNotNullExecutor.getExecutorId(), result.getExecutor().getId()),
                () -> assertNotNull(claimDtoNotNullExecutor.getExecutorId()),
                () -> assertNotNull(result.getExecutor()),
                () -> assertEquals(claimDtoNotNullExecutor.getCreateDate(), result.getCreateDate().toEpochMilli()),
                () -> assertEquals(claimDtoNotNullExecutor.getPlanExecuteDate(), result.getPlanExecuteDate().toEpochMilli())

        );

        // deleting result entity
        claimRepository.deleteById(claimDtoNotNullExecutor.getId());
    }


    @Test
    public void getClaimShouldPassSuccess() {
        int claimId = 1;
        ClaimDto expected = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        ClaimDto result = sut.getClaim(claimId);
        assertAll(
                () -> assertEquals(expected.getDescription(), result.getDescription()),
                () -> assertEquals(expected.getCreatorId(), result.getCreatorId()),
                () -> assertEquals(expected.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(expected.getStatus(), result.getStatus()),
                () -> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(expected.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(expected.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(expected.getId(), result.getId())
        );
    }


    @Test
    public void updateClaimShouldPassSuccessNotNull() {
        // given
        int claimId = 4;
        ClaimDto given = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        String newTitle = "new title";
        given.setExecutorId(userRepository.findUserById(1).getId());
        given.setTitle(newTitle);

        ClaimDto result = sut.updateClaim(given);

        assertAll(
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getTitle(), result.getTitle()),
                () -> assertEquals(given.getCreatorId(), result.getCreatorId()),
                () -> assertEquals(given.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate())
        );

        given.setTitle("title4");
        claimRepository.save(Objects.requireNonNull(conversionService.convert(given, Claim.class)));
    }


    @Test
    public void updateClaimShouldPassSuccessExecutorNull() {
        // given
        int claimId = 1;
        ClaimDto given = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        String newTitle = "new title";
        given.setTitle(newTitle);

        ClaimDto result = sut.updateClaim(given);

        assertAll(
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getTitle(), result.getTitle()),
                () -> assertEquals(given.getCreatorId(), result.getCreatorId()),
                () -> assertEquals(given.getExecutorId(), result.getExecutorId()),
                () -> assertNull(result.getExecutorId()),
                () -> assertNull(given.getExecutorId()),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate())
        );

        given.setTitle("title4");
        claimRepository.save(Objects.requireNonNull(conversionService.convert(given, Claim.class)));
    }

    @Test
    public void changeStatusShouldPassSuccess() {
        int claimId = 4;
        int claimId2 = 5;
        ClaimDto resultExecuted = sut.changeStatus(claimId, EXECUTED);
        ClaimDto resultOpen = sut.changeStatus(claimId2, OPEN);
        assertEquals(EXECUTED, resultExecuted.getStatus());
        assertNotNull(resultExecuted.getFactExecuteDate());
        assertEquals(OPEN, resultOpen.getStatus());
//        assertNull(resultOpen.getFactExecuteDate());
        Claim claim = claimRepository.findById(4).get();
        claim.setStatus(IN_PROGRESS);
        claimRepository.save(claim);
    }

    @Test
    public void changeStatusNotShouldPassSuccessWrongId() {
        int claimId = 12;
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(claimId, EXECUTED));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(3, OPEN));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(4, StatusE.CANCELLED));
    }


    @Test
    public void getClaimCommentShouldPassSuccess() {
        int claimCommentId = 3;
        ClaimCommentDto expected = conversionService.convert(claimCommentRepository.findClaimCommentById(claimCommentId),
                ClaimCommentDto.class);
        ClaimCommentDto result = sut.getClaimComment(claimCommentId);
        assertAll(
                () -> assertEquals(expected.getClaimId(), result.getClaimId()),
                () -> assertEquals(expected.getDescription(), result.getDescription()),
                () -> assertEquals(expected.getCreatorId(), result.getCreatorId()),
                () -> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(expected.getId(), result.getId())
        );
    }


    @Test
    public void getAllClaimsComment() {
        List<ClaimCommentDto> claimCommentDto = sut.getAllClaimsComments(1);
        assertEquals(2, claimCommentDto.size());
    }


    @Test
    public void createClaimCommentShouldPassSuccess() {
        // given
        ClaimCommentDto claimCommentDto = getClaimCommentDto();

        claimCommentDto.setCreatorId(userRepository.findUserById(claimCommentDto.getCreatorId()).getId());
        claimCommentDto.setClaimId(claimRepository.findClaimById(claimCommentDto.getClaimId()).getId());

        ClaimCommentDto idNotNullExecutor = sut.createClaimComment(2, claimCommentDto);
        assertNotNull(idNotNullExecutor);
        ClaimComment result = claimCommentRepository.findById(idNotNullExecutor.getId()).get();

        assertAll(
                () -> assertEquals(claimCommentDto.getDescription(), result.getDescription()),
                () -> assertEquals(claimCommentDto.getCreatorId(), result.getCreator().getId()),
                () -> assertEquals(claimCommentDto.getCreateDate(), result.getCreateDate().toEpochMilli()),
                () -> assertEquals(claimCommentDto.getClaimId(), result.getClaim().getId())

        );

        // deleting result entity
        claimCommentRepository.deleteById(idNotNullExecutor.getId());
    }


    @Test
    public void updateClaimCommentShouldPassSuccess() {
        // given
        int claimCommentId = 4;
        ClaimCommentDto given = conversionService.convert(claimCommentRepository.findClaimCommentById(claimCommentId),
                ClaimCommentDto.class);
        String newDescription = "new title";
        given.setDescription(newDescription);

        ClaimCommentDto result = sut.updateClaimComment(given);

        assertAll(
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getCreatorId(), result.getCreatorId()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getClaimId(), result.getClaimId())
        );

        given.setDescription("claim4-description");
        claimCommentRepository.save(Objects.requireNonNull(conversionService.convert(given, ClaimComment.class)));
    }


}
