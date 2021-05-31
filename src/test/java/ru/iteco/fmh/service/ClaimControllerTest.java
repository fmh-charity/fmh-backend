package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.ClaimController;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.dto.user.UserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class ClaimControllerTest {
    @Autowired
    ClaimController sut;

    @Autowired
    ClaimRepository claimRepository;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Autowired
    UserRepository userRepository;

    @Test
    public void createClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        ClaimDto given = getClaimDto();
        given.setCreator(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setExecutor(conversionService.convert(userRepository.findUserById(1), UserDto.class));
        given.setId(6);

        ClaimDto result = sut.createClaim(given);

        assertAll(
                ()-> assertEquals(given.getId(), result.getId()),
                ()-> assertEquals(given.getDescription(), result.getDescription()),
                ()-> assertEquals(given.getCreator(), result.getCreator()),
                ()-> assertEquals(given.getExecutor(), result.getExecutor()),
                ()-> assertEquals(given.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
                ()-> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                ()-> assertEquals(given.getStatus(), result.getStatus()),
                ()-> assertEquals(given.getComment(), result.getComment())
        );

        // deleting result entity
        claimRepository.deleteById(result.getId());
    }

    @Test
    public void getClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int claimId=1;

        ClaimDto expected = conversionService.convert(claimRepository.findById(claimId).get(), ClaimDto.class);

        ClaimDto result = sut.getClaim(claimId);

        assertAll(
                ()-> assertEquals(expected.getDescription(), result.getDescription()),
                ()-> assertEquals(expected.getCreator(), result.getCreator()),
                ()-> assertEquals(expected.getExecutor(), result.getExecutor()),
                ()-> assertEquals(expected.getStatus(), result.getStatus()),
                ()-> assertEquals(expected.getComment(), result.getComment()),
                ()-> assertEquals(expected.getCreateDate(), result.getCreateDate()),
                ()-> assertEquals(expected.getFactExecuteDate(), result.getFactExecuteDate()),
                ()-> assertEquals(expected.getPlanExecuteDate(), result.getPlanExecuteDate()),
                ()-> assertEquals(expected.getId(), result.getId())

        );
    }

    @Test
    public void getAllActiveNotesSort() {
        List<ClaimShortInfoDto> claimShortInfoDtoList = sut.getAllClaims();
        assertEquals(5, claimShortInfoDtoList.size());
        assertTrue(claimShortInfoDtoList.get(1).getPlanExecuteDate().isBefore
                (claimShortInfoDtoList.get(2).getPlanExecuteDate()));
        }

}
