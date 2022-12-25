package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.dto.wish.WishCreationInfoDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.task.Status.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishServiceTest {
    @Autowired
    WishService sut;

    @MockBean
    WishRepository wishRepository;

    @MockBean
    WishCommentRepository wishCommentRepository;
    @MockBean
    UserRepository userRepository;


    @Autowired
    ConversionService conversionService;

    @Test
    public void getOpenInProgressWishesShouldPassSuccess() {
        // given
        List<Wish> wishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = wishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(any()))
                .thenReturn(wishList);
        List<WishDto> result = sut.getOpenInProgressWishes();

        assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
    public void getAllWishesShouldPassSuccess() {
        // given
        List<Wish> wishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = wishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());
        Pageable pageableList = PageRequest.of(0, 8, Sort.by("planExecuteDate").and(Sort.by("createDate").descending()));
        Page<Wish> pageableResult = new PageImpl<>(wishList, pageableList, 8);

        doReturn(pageableResult).when(wishRepository).findAllByCurrentRoles(any(), any(), any(), any());
        List<WishDto> result = sut.getWishes(0, 8, null, true)
                .getElements().stream().toList();

        assertEquals(expected, result);
    }

    @Test
    public void createWishShouldPassSuccess() {
        // given
        WishCreationInfoDto wishCreationInfoDto = getWishCreationInfoDto(IN_PROGRESS);
        Wish wish = conversionService.convert(wishCreationInfoDto, Wish.class);
        wish.setId(12);
        wish.setCreator(getUser());
        wish.setPatient(getPatient());

        when(wishRepository.save(any())).thenReturn(wish);
        WishDto result = sut.createWish(wishCreationInfoDto);

//        assertAll(
//                () -> assertEquals(wish.getId(), result.getId()),
//                () -> assertEquals(wish.getDescription(), result.getDescription()),
//                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), result.getPlanExecuteDate()),
//                () -> assertEquals(wish.getCreateDate().toEpochMilli(), result.getCreateDate()),
//                () -> assertEquals(wish.getStatus(), result.getStatus()),
//                () -> assertEquals(conversionService.convert(wish.getExecutor(), UserDtoIdFio.class), result.getExecutor()),
//                () -> assertEquals(conversionService.convert(wish.getCreator(), UserDtoIdFio.class), result.getCreator()),
//                () -> assertEquals(conversionService.convert(wish.getPatient(), PatientDtoIdFio.class), result.getPatient()),
//                () -> assertNull(result.getExecutor()),
//                () -> assertNotNull(result.getCreator())
//        );
    }


    @Test
    public void getWishShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        wish.setExecutor(null);
        int wishId = 1;

        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));
        WishDto expected = conversionService.convert(wish, WishDto.class);
        WishDto result = sut.getWish(wishId);

        assertEquals(expected, result);
    }


    @Test
    public void changeStatusOpenToCancelledShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(OPEN);

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, CANCELLED, null, null);
        assertEquals(CANCELLED, result.getStatus());
    }

    @Test
    public void changeStatusOpenToInProgressShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(OPEN);
        User user = getUser();

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, IN_PROGRESS, user.getId(), null);
        assertEquals(IN_PROGRESS, result.getStatus());
    }

    @Test
    public void changeStatusInProgressToExecutedShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(IN_PROGRESS);
        givenWish.setFactExecuteDate(null);

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, EXECUTED, null, getWishCommentDto(EXECUTED));
        assertEquals(EXECUTED, result.getStatus());
        assertNotNull(result.getFactExecuteDate());
    }

    @Test
    public void changeStatusInProgressToCancelledShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish inProgressWish = getWish(IN_PROGRESS);

        when(wishRepository.findById(any())).thenReturn(Optional.of(inProgressWish));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, CANCELLED, null, null));
        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, CANCELLED, null, null));
    }

    @Test
    public void changeStatusOpenToExecutedShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish openWish = getWish(OPEN);

        when(wishRepository.findById(any())).thenReturn(Optional.of(openWish));

        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, EXECUTED, null, null));
    }

    @Test
    public void changeStatusCancelledToAnyShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish cancelledWish = getWish(CANCELLED);

        when(wishRepository.findById(any())).thenReturn(Optional.of(cancelledWish));
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, EXECUTED, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, OPEN, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, IN_PROGRESS, null, null))
        );
    }

    @Test
    public void changeStatusExecutedToAnyShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish executedWish = getWish(EXECUTED);

        when(wishRepository.findById(any())).thenReturn(Optional.of(executedWish));
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, CANCELLED, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, OPEN, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, IN_PROGRESS, null, null))
        );
    }

    @Test
    public void getPatientAllWishesShouldPassSuccess() {
        // given
        Integer patientId = 1;
        List<Wish> allWishList = List.of(getWish(OPEN), getWish(EXECUTED), getWish(CANCELLED), getWish(IN_PROGRESS));
        List<WishDto> expected = allWishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(patientId))
                .thenReturn(allWishList);
        List<WishDto> result = sut.getPatientAllWishes(patientId);

        assertEquals(expected, result);
    }

    @Test
    public void getPatientOpenInProgressWishes() {
        // given
        Integer patientId = 1;

        List<Wish> openInProgressWishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = openInProgressWishList.stream()
                .map(wish -> conversionService.convert(wish, WishDto.class)).collect(Collectors.toList());

        when(wishRepository.findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
                patientId, List.of(OPEN, IN_PROGRESS))).thenReturn(openInProgressWishList);
        List<WishDto> result = sut.getPatientOpenInProgressWishes(patientId);

        assertEquals(expected, result);
    }


    @Test
    public void getWishCommentShouldPassSuccess() {
        // given
        WishComment wishComment = getWishComment(OPEN);
        int wishCommentId = 1;

        when(wishCommentRepository.findById(any())).thenReturn(Optional.of(wishComment));
        WishCommentInfoDto expected = conversionService.convert(wishComment, WishCommentInfoDto.class);
        WishCommentInfoDto result = sut.getWishComment(wishCommentId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishCommentsShouldPassSuccess() {
        // given
        List<WishComment> wishCommentList = List.of(getWishComment(OPEN), getWishComment(IN_PROGRESS));
        List<WishCommentInfoDto> expected = wishCommentList.stream()
                .map(wishComment -> conversionService.convert(wishComment, WishCommentInfoDto.class))
                .collect(Collectors.toList());
        int wishId = 1;

        when(wishCommentRepository.findAllByWish_Id(any())).thenReturn(wishCommentList);
        List<WishCommentInfoDto> result = sut.getAllWishComments(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void createWishCommentShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        int wishId = 1;
        WishComment wishComment = getWishComment(OPEN);
        int commentId = 1;
        wishComment.setId(commentId);
        WishCommentDto wishCommentDto = conversionService.convert(wishComment, WishCommentDto.class);

        when(wishCommentRepository.save(any())).thenReturn(wishComment);
        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));

        WishCommentInfoDto result = sut.createWishComment(wishId, wishCommentDto);

        assertAll(
                () -> assertEquals(wishCommentDto.getId(), result.getId()),
                () -> assertEquals(wishCommentDto.getDescription(), result.getDescription()),
                () -> assertEquals(wishCommentDto.getCreateDate(), result.getCreateTime()),
                () -> assertEquals(wishCommentDto.getCreatorId(), result.getUserDtoIdFio().id())
        );
    }
}