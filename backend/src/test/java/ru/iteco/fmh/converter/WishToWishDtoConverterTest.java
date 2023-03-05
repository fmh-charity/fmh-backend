package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoIdFioConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.converter.wish.WishToWishDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.wish.Wish;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getWish;
import static ru.iteco.fmh.model.wish.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.wish.Status.OPEN;


class WishToWishDtoConverterTest {

    private final PatientToPatientDtoIdFioConverter toPatientDtoIdFio = new PatientToPatientDtoIdFioConverter();
    private final UserToUserDtoIdFioConverter toUserDtoIdFioConverter = new UserToUserDtoIdFioConverter();
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter = new RoomEntityToRoomDtoRsConverter();
    private final WishToWishDtoConverter wishToWishDtoConverter
            = new WishToWishDtoConverter(roomEntityToRoomDtoRsConverter, toPatientDtoIdFio, toUserDtoIdFioConverter);

    @Test
    void convertWishForOpen() {
        Wish wish = getWish(OPEN);


        WishDto dto = wishToWishDtoConverter.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(wish.getPatient().getId(), dto.getPatient().id()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), dto.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), wish.getFactExecuteDate().toEpochMilli()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(dto.getCreator().id(), wish.getCreator().getId()),
                () -> assertNull(dto.getExecutor())

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
                () -> assertEquals(dto.getCreator().id(), wish.getCreator().getId())
        );
    }
}




