package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.ClaimController;
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


import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.converter.ClaimDtoToClaimConverterTest.getClaimDto2;
import static ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter.getUserDto;
import static ru.iteco.fmh.model.task.StatusE.EXECUTED;
import static ru.iteco.fmh.model.task.StatusE.IN_PROGRESS;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ClaimControllerTest {
    @Autowired
    ClaimController sut;
    @Autowired
    ClaimRepository claimRepository;
    @Autowired
    ClaimCommentRepository claimCommentRepository;
    @Autowired
    ConversionServiceFactoryBean factoryBean;
    @Autowired
    UserRepository userRepository;


    @Test
    public void getAllClaims() {
        List<ClaimDto> claimDtoList = sut.getAllClaims();
        for (ClaimDto claimDto : claimDtoList) {
            System.out.println(claimDto);
        }
        assertEquals(5, claimDtoList.size());

    }

    @Test
    public void getAllOpenAndInProgressClaims() {
        List<ClaimDto> claimDtoList = sut.getOpenInProgressClaims();
        for (ClaimDto claimDto : claimDtoList) {
            System.out.println(claimDto);
        }
        assertEquals(4, claimDtoList.size());
        assertTrue(claimDtoList.get(1).getPlanExecuteDate().isBefore
                (claimDtoList.get(2).getPlanExecuteDate()));
    }

    @Test
    public void createClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        ClaimDto given = getClaimDto2();
        System.out.println("1" + given.getStatus());
        //executor notNull
        given.setCreator(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));

        Integer idNotNullExecutor = sut.createClaim(given);
        System.out.println("2" + claimRepository.findById(idNotNullExecutor).get().getStatus());
        assertNotNull(idNotNullExecutor);
        Claim  result = claimRepository.findById(idNotNullExecutor).get();
        System.out.println("3" + result.getStatus());
        assertEquals(result.getStatus(), StatusE.IN_PROGRESS);

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
        ConversionService conversionService = factoryBean.getObject();
        int claimId=1;
        ClaimDto expected = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        ClaimDto result = sut.getClaim(claimId);
        assertAll(
                ()-> assertEquals(expected.getDescription(), result.getDescription()),
                ()-> assertEquals(expected.getCreator(), result.getCreator()),
                ()-> assertEquals(expected.getExecutor(), result.getExecutor()),
                ()-> assertEquals(expected.getStatus(), result.getStatus()),
                ()-> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(expected.getFactExecuteDate(), result.getFactExecuteDate()),
                ()-> assertEquals(expected.getPlanExecuteDate(), result.getPlanExecuteDate()),
                ()-> assertEquals(expected.getId(), result.getId())
        );
    }


    @Test
    public void updateClaimShoulNotdPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int claimId = 3;
        ClaimDto given = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        String newTitle = "new title";
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setTitle(newTitle);
        assertThrows(IllegalArgumentException.class, () -> sut.updateClaim(given));
    }

    @Test
    public void updateClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int claimId = 4;
        ClaimDto given = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);
        String newTitle = "new title";
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setTitle(newTitle);

        ClaimDto result = sut.updateClaim(given);

        assertAll(
                ()-> assertEquals(given.getDescription(), result.getDescription()),
                ()-> assertEquals(given.getTitle(), result.getTitle()),
                ()-> assertEquals(given.getCreator(), result.getCreator()),
                ()-> assertEquals(given.getExecutor(), result.getExecutor()),
                ()-> assertEquals(given.getStatus(), result.getStatus()),
                ()-> assertEquals(given.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                ()-> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate())
        );
    }

    @Test
    public void changeStatusShouldPassSuccess() {
        int claimId = 4;
        int claimId2 = 5;
        ClaimDto resultCancelled = sut.changeStatus(claimId, EXECUTED);
        ClaimDto resultCancelled2 = sut.changeStatus(claimId2,StatusE.OPEN);
        assertEquals(EXECUTED, resultCancelled.getStatus());
        assertEquals(LocalDateTime.now().withNano(0),resultCancelled.getFactExecuteDate());
        assertEquals(StatusE.OPEN, resultCancelled2.getStatus());
//        assertNull(resultCancelled2.getFactExecuteDate());
        Claim claim = claimRepository.findById(4).get();
        claim.setStatus(IN_PROGRESS);
        claimRepository.save(claim);
    }
    @Test
    public void changeStatusNotShouldPassSuccessWrongId() {
        int claimId = 12;
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(claimId, EXECUTED));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(3, StatusE.OPEN));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(4, StatusE.CANCELLED));
    }


    @Test
    public void getClaimCommentShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();
        int claimCommentId = 3;
        ClaimCommentDto expected = conversionService.convert(claimCommentRepository.findClaimCommentById(claimCommentId),
                ClaimCommentDto.class);
        ClaimCommentDto result = sut.getClaimComment(claimCommentId);
        assertAll(
                ()-> assertEquals(expected.getClaim(), result.getClaim()),
                ()-> assertEquals(expected.getDescription(), result.getDescription()),
                ()-> assertEquals(expected.getCreator(), result.getCreator()),
                ()-> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(expected.getId(), result.getId())
        );
    }


    @Test
    public void getAllClaimsComment() {
        List<ClaimCommentDto> claimCommentDto = sut.getAllClaimsComments(1);
        for (ClaimCommentDto claimDto : claimCommentDto) {
            System.out.println(claimDto);
        }
        assertEquals(2, claimCommentDto.size());

    }


    @Test
    public void createClaimCommentShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        ClaimCommentDto claimCommentDto = ClaimCommentDto.builder()
                .id(24)
                .claim(getClaimDto2())
                .creator(getUserDto())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        //executor notNull
        claimCommentDto.setCreator(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        claimCommentDto.setClaim(conversionService.convert(claimRepository.findClaimById(1), ClaimDto.class));

        Integer idNotNullExecutor = sut.createClaimComment(1,claimCommentDto);
        assertNotNull(idNotNullExecutor);
        ClaimComment  result = claimCommentRepository.findById(idNotNullExecutor).get();

        assertAll(
                () -> assertEquals( claimCommentDto.getDescription(), result.getDescription()),
                () -> assertEquals( claimCommentDto.getCreator(), conversionService.convert(result.getCreator(), UserDto.class)),
                () -> assertEquals( claimCommentDto.getCreateDate().withNano(0), result.getCreateDate().withNano(0)),
                () -> assertEquals( claimCommentDto.getClaim(), conversionService.convert(result.getClaim(), ClaimDto.class))

        );

        // deleting result entity
        claimCommentRepository.deleteById(idNotNullExecutor);
    }


    @Test
    public void updateClaimCommentShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int claimCommentId = 4;
        ClaimCommentDto given = conversionService.convert(claimCommentRepository.findClaimCommentById(claimCommentId),
                ClaimCommentDto.class);
        String newDescription = "new title";
        given.setDescription(newDescription);

        ClaimCommentDto result = sut.updateClaimComment(given);

        assertAll(
                ()-> assertEquals(given.getDescription(), result.getDescription()),
                ()-> assertEquals(given.getCreator(), result.getCreator()),
                ()-> assertEquals(given.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(given.getClaim(), result.getClaim())

        );
    }




}
