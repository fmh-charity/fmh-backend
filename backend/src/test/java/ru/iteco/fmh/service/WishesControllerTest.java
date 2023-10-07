package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.WishesController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.*;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.security.UserDetailsServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.wish.Status.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
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
    @WithUserDetails()
    public void getAllWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title2", "wish-title5");

        List<String> result = Objects.requireNonNull(
                        sut.getWishes(0, 8, "IN_PROGRESS", "desc", "status")
                                .getBody()).getElements().stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    @WithUserDetails()
    public void createOpenWishShouldPassSuccess() {
       // given
        WishCreationRequest wishCreationRequest = getWishCreationInfoDto();

        WishDto givenWish = sut.createWish(wishCreationRequest);
        assertEquals(OPEN, givenWish.getStatus());
        Integer wishId = givenWish.getId();
        assertNotNull(givenWish);

        Wish wishResult = wishRepository.findWishById(givenWish.getId());
        assertNotNull(wishResult);
        WishDto expectedWish = conversionService.convert(wishResult, WishDto.class);
        givenWish.setCreator(expectedWish.getCreator());

        assertEquals(givenWish, expectedWish);

        // AFTER - deleting result entity
        wishRepository.deleteById(wishId);
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
    @WithUserDetails
    public void changeStatusInProgressToExecutedShouldPassSuccess() {
        // given
        int wishInProgressId = 5;

        WishDto result = sut.changeStatus(wishInProgressId, EXECUTED, null, getWishCommentDto());

        assertEquals(EXECUTED, result.getStatus());
        assertNotNull(result.getFactExecuteDate());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    @Test
    @WithUserDetails
    public void changeStatusInProgressToOpenShouldPassSuccess() {
        // given
        int wishInProgressId = 2;
        WishCommentDto wishCommentDto = getWishCommentDto();
        WishDto result = sut.changeStatus(wishInProgressId, OPEN, null, wishCommentDto);
        assertNotNull(wishCommentRepository.findById(wishCommentDto.getId()));
        assertEquals(OPEN, result.getStatus());
        assertEquals(2, result.getWishExecutors().size());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    // тесты на смену неверных статусов имеются в WishServiceTest

    @Test
    public void getWishCommentShouldPassSuccess() {
        // given
        int commentId = 2;

        WishCommentInfoDto expected = conversionService.convert(wishCommentRepository.findById(commentId).get(), WishCommentInfoDto.class);

        WishCommentInfoDto result = sut.getWishComment(commentId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishCommentsShouldPassSuccess() {
        // given
        int wishId = 1;
        List<WishCommentInfoDto> expected = wishCommentRepository.findAllByWish_Id(wishId).stream()
                .map(i -> conversionService.convert(i, WishCommentInfoDto.class))
                .collect(Collectors.toList());

        List<WishCommentInfoDto> result = sut.getAllWishComments(wishId);

        assertEquals(expected, result);
    }

    @Test
    @WithUserDetails
    public void createWishCommentShouldPassSuccess() {
        // given
        WishCommentDto givenWishCommentDto = getWishCommentDto();

        givenWishCommentDto.setCreatorId(userRepository.findUserById(1).getId());
        givenWishCommentDto.setWishId(wishRepository.findWishById(1).getId());

        WishCommentInfoDto resultId = sut.createWishComment(1, givenWishCommentDto);
        assertNotNull(resultId);

        // AFTER - deleting result entity
        wishCommentRepository.deleteById(resultId.getId());
    }

    @Test
    @WithUserDetails()
    public void getWishByWishCreatorWithoutNecessaryRoleShouldPassSuccess() {
        // given
        WishCreationRequest wishCreationRequest = getWishCreationInfoDto(OPEN);
        wishCreationRequest.setWishVisibility(List.of(1));

        WishDto givenWish = sut.createWish(wishCreationRequest);
        Integer wishId = givenWish.getId();
        assertNotNull(givenWish);

        Wish wishResult = wishRepository.findWishById(givenWish.getId());
        assertNotNull(wishResult);
        WishDto expectedWish = conversionService.convert(wishResult, WishDto.class);
        givenWish.setCreator(expectedWish.getCreator());

        assertEquals(givenWish, expectedWish);

        // AFTER - deleting result entity
        wishRepository.deleteById(wishId);

    }

    @Test
    @WithUserDetails()
    public void getAllWishesByNecessaryRoleAndWishCreatorShouldPassSuccess() {
        // given
        WishPaginationDto response = sut.getWishes(0, 8, "OPEN", "desc", "status").getBody();

        assertNotNull(response);
        assertEquals(3, response.getElements().size());
    }

    @Test
    @WithMockUser(username = "login3", password = "password3", roles = "MEDICAL_WORKER")
    public void getAllOpenInProgressWishesByNecessaryRoleAndWishCreatorShouldPassSuccess() {
        // given
        List<WishDto> list = sut.getAllOpenInProgressWishes();

        assertNotNull(list);
        assertEquals(5, list.size());
    }
}
