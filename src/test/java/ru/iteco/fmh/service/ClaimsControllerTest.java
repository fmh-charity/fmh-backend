package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.ClaimsController;
import ru.iteco.fmh.dao.repository.ClaimCommentRepository;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.task.StatusE.*;


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
        assertTrue(claimDtoList.get(3).getPlanExecuteDate().isBefore
                (claimDtoList.get(4).getPlanExecuteDate()));
        assertTrue(claimDtoList.get(1).getCreateDate().isBefore
                (claimDtoList.get(2).getCreateDate()));

    }

    @Test
    public void getAllOpenAndInProgressClaims() {
        List<ClaimDto> claimDtoList = sut.getOpenInProgressClaims();

        assertEquals(4, claimDtoList.size());
        assertTrue(claimDtoList.get(1).getPlanExecuteDate().isBefore
                (claimDtoList.get(2).getPlanExecuteDate()));
        assertTrue(claimDtoList.get(0).getCreateDate().isBefore
                (claimDtoList.get(1).getCreateDate()));
    }

    @Test
    public void createClaimShouldPassSuccess() {
        // given
        ClaimDto given = getClaimDtoOpen();
        //executor notNull
        given.setCreator(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        Integer idNotNullExecutor = sut.createClaim(given);
        assertNotNull(idNotNullExecutor);
        Claim result = claimRepository.findById(idNotNullExecutor).get();
        assertEquals(given.getStatus(), IN_PROGRESS);

        assertAll(
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getTitle(), result.getTitle()),
                () -> assertEquals(given.getCreator(), conversionService.convert(result.getCreator(), UserDto.class)),
                () -> assertEquals(given.getExecutor(), conversionService.convert(result.getExecutor(), UserDto.class)),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate())
        );

        // deleting result entity
        claimRepository.deleteById(idNotNullExecutor);
    }


    @Test
    public void getClaimShouldPassSuccess() {
        int claimId = 1;
        ClaimDto expected = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        ClaimDto result = sut.getClaim(claimId);
        assertAll(
                () -> assertEquals(expected.getDescription(), result.getDescription()),
                () -> assertEquals(expected.getCreator(), result.getCreator()),
                () -> assertEquals(expected.getExecutor(), result.getExecutor()),
                () -> assertEquals(expected.getStatus(), result.getStatus()),
                () -> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(expected.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(expected.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(expected.getId(), result.getId())
        );
    }


    @Test
    public void updateClaimShouldPassSuccess() {
        // given
        int claimId = 4;
        ClaimDto given = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        String newTitle = "new title";
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setTitle(newTitle);

        ClaimDto result = sut.updateClaim(given);

        assertAll(
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getTitle(), result.getTitle()),
                () -> assertEquals(given.getCreator(), result.getCreator()),
                () -> assertEquals(given.getExecutor(), result.getExecutor()),
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
        assertEquals(LocalDateTime.now().withNano(0), resultExecuted.getFactExecuteDate());
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
                () -> assertEquals(expected.getClaim(), result.getClaim()),
                () -> assertEquals(expected.getDescription(), result.getDescription()),
                () -> assertEquals(expected.getCreator(), result.getCreator()),
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
        ClaimCommentDto claimCommentDto = ClaimCommentDto.builder()
                .id(24)
                .claim(getClaimDtoInProgress())
                .creator(getUserDto())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        //executor notNull
        claimCommentDto.setCreator(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        claimCommentDto.setClaim(conversionService.convert(claimRepository.findClaimById(1), ClaimDto.class));

        Integer idNotNullExecutor = sut.createClaimComment(1, claimCommentDto);
        assertNotNull(idNotNullExecutor);
        ClaimComment result = claimCommentRepository.findById(idNotNullExecutor).get();

        assertAll(
                () -> assertEquals(claimCommentDto.getDescription(), result.getDescription()),
                () -> assertEquals(claimCommentDto.getCreator(), conversionService.convert(result.getCreator(), UserDto.class)),
                () -> assertEquals(claimCommentDto.getCreateDate().withNano(0), result.getCreateDate().withNano(0)),
                () -> assertEquals(claimCommentDto.getClaim(), conversionService.convert(result.getClaim(), ClaimDto.class))

        );

        // deleting result entity
        claimCommentRepository.deleteById(idNotNullExecutor);
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
                () -> assertEquals(given.getCreator(), result.getCreator()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getClaim(), result.getClaim())
        );

        given.setDescription("claim4-description");
        claimCommentRepository.save(Objects.requireNonNull(conversionService.convert(given, ClaimComment.class)));
    }


}
