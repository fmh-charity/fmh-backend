package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.WishesController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.security.UserDetailsServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getWishCommentDto;
import static ru.iteco.fmh.TestUtils.getWishDto;
import static ru.iteco.fmh.model.task.Status.CANCELLED;
import static ru.iteco.fmh.model.task.Status.EXECUTED;
import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class WishesControllerTest {
    @Autowired
    WishesController sut;

    @Autowired
    WishRepository wishRepository;

    @Autowired
    WishCommentRepository wishCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ConversionService conversionService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    public void getOpenInProgressWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6", "wish-title5", "wish-title2");

        List<String> result = sut.getAllOpenInProgressWishes().stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title4", "wish-title3", "wish-title2");

        List<String> result = Objects.requireNonNull(
                sut.getWishes(0, 8, List.of(IN_PROGRESS, EXECUTED, CANCELLED), true)
                    .getBody()).getElements().stream()
                    .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void createInProgressWishShouldPassSuccess() {
        //given
        WishDto givenWishDto = getWishDto();

        givenWishDto.setCreatorId(userRepository.findUserById(1).getId());
        givenWishDto.setExecutorId(userRepository.findUserById(1).getId());
        givenWishDto.setPatientId(patientRepository.findPatientById(1).getId());

        WishDto result = sut.createWish(givenWishDto);
        assertNotNull(result.getId());

        Wish result2 = wishRepository.findById(result.getId()).get();

        assertEquals(IN_PROGRESS, result2.getStatus());

        // AFTER - deleting result entity
        wishRepository.deleteById(result.getId());
    }

    @Test
    public void createOpenWishShouldPassSuccess() {
        //given
        WishDto givenWishDto = getWishDto();
        givenWishDto.setCreatorId(userRepository.findUserById(1).getId());
        givenWishDto.setExecutorId(null);
        givenWishDto.setPatientId(patientRepository.findPatientById(1).getId());

        WishDto resultId = sut.createWish(givenWishDto);
        assertNotNull(resultId);

        Wish result = wishRepository.findById(resultId.getId()).get();

        assertEquals(OPEN, result.getStatus());

        // AFTER - deleting result entity
        wishRepository.deleteById(resultId.getId());
    }

    @Test
    public void getWishShouldPassSuccess() {
        int wishId = 1;
        WishDto expected = conversionService.convert(wishRepository.findById(wishId).get(), WishDto.class);
        WishDto result = sut.getWish(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void changeStatusOpenToCancelledShouldPassSuccess() {
        // given
        int wishOpenId = 7;

        WishDto result = sut.changeStatus(wishOpenId, CANCELLED, null, null);

        assertEquals(CANCELLED, result.getStatus());

        // after
        Wish wish = wishRepository.findById(wishOpenId).get();
        wish.setStatus(OPEN);
        wishRepository.save(wish);
    }

    @Test
    public void changeStatusOpenToInProgressShouldPassSuccess() {
        // given
        int wishOpenId = 1;

        WishDto result = sut.changeStatus(wishOpenId, IN_PROGRESS, userRepository.findUserById(1).getId(), null);

        assertEquals(IN_PROGRESS, result.getStatus());

        // after
        Wish wish = wishRepository.findById(wishOpenId).get();
        wish.setStatus(OPEN);
        wishRepository.save(wish);
    }


    @Test
    public void changeStatusInProgressToExecutedShouldPassSuccess() {
        // given
        int wishInProgressId = 2;

        WishDto result = sut.changeStatus(wishInProgressId, EXECUTED, null, getWishCommentDto(EXECUTED));

        assertEquals(EXECUTED, result.getStatus());
        assertNotNull(result.getFactExecuteDate());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    @Test
    public void changeStatusInProgressToOpenShouldPassSuccess() {
        // given
        int wishInProgressId = 2;
        WishCommentDto wishCommentDto = getWishCommentDto(OPEN);
        WishDto result = sut.changeStatus(wishInProgressId, OPEN, null, wishCommentDto);
        assertNotNull(wishCommentRepository.findById(wishCommentDto.getId()));
        assertEquals(OPEN, result.getStatus());
        assertNull(result.getExecutorId());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    // тесты на смену неверных статусов имеются в WishServiceTest

    @Test
    public void getWishCommentShouldPassSuccess() {
        // given
        int commentId = 1;

        WishCommentDto expected = conversionService.convert(wishCommentRepository.findById(commentId).get(), WishCommentDto.class);

        WishCommentDto result = sut.getWishComment(commentId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishCommentsShouldPassSuccess() {
        // given
        int wishId = 1;
        List<WishCommentDto> expected = wishCommentRepository.findAllByWish_Id(wishId).stream()
                .map(i -> conversionService.convert(i, WishCommentDto.class))
                .collect(Collectors.toList());

        List<WishCommentDto> result = sut.getAllWishComments(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void createWishCommentShouldPassSuccess() {
        // given
        WishCommentDto givenWishCommentDto = getWishCommentDto(OPEN);

        givenWishCommentDto.setCreatorId(userRepository.findUserById(1).getId());
        givenWishCommentDto.setWishId(wishRepository.findWishById(1).getId());

        WishCommentDto resultId = sut.createWishComment(1, givenWishCommentDto);
        assertNotNull(resultId);

        // AFTER - deleting result entity
        wishCommentRepository.deleteById(resultId.getId());
    }
}