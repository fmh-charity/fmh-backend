package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoIdFioConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.converter.wish.WishToWishDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getWish;
import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;


class WishToWishDtoConverterTest {

    PatientToPatientDtoIdFioConverter toPatientDtoIdFio = new PatientToPatientDtoIdFioConverter();
    UserToUserDtoIdFioConverter toUserDtoIdFioConverter = new UserToUserDtoIdFioConverter();
    WishToWishDtoConverter wishToWishDtoConverter = new WishToWishDtoConverter(toPatientDtoIdFio, toUserDtoIdFioConverter);

    @Test
    void convertWishForOpen() {
        Wish wish = getWish(OPEN);
        wish.setExecutor(null);

        WishDto dto = wishToWishDtoConverter.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(wish.getPatient().getId(), dto.getPatient().id()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), dto.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), wish.getFactExecuteDate().toEpochMilli()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(dto.getCreatorId(), wish.getCreator().getId()),
                () -> assertEquals(dto.getExecutor(), wish.getExecutor()),
                () -> assertNull(dto.getExecutor()),
                () -> assertNull(wish.getExecutor())
        );
    }

    @Test
    void convertWishForInProgress() {
        Wish wish = getWish(IN_PROGRESS);

        WishDto dto = wishToWishDtoConverter.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(wish.getPatient().getId(), dto.getPatient().id()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), dto.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), wish.getFactExecuteDate().toEpochMilli()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(dto.getCreatorId(), wish.getCreator().getId()),
                () -> assertEquals(dto.getExecutor().id(), wish.getExecutor().getId()),
                () -> assertNotNull(dto.getExecutor()),
                () -> assertNotNull(wish.getExecutor())
        );
    }

}




