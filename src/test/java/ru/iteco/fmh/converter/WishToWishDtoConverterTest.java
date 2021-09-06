package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.converter.wish.WishToWishDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getWish;
import static ru.iteco.fmh.model.task.StatusE.OPEN;


class WishToWishDtoConverterTest {
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    WishToWishDtoConverter convertor = new WishToWishDtoConverter(patientToPatientDtoConverter, userToUserDtoConverter);

    @Test
    void convert() {
        Wish wish = getWish(OPEN);
        WishDto dto = convertor.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(patientToPatientDtoConverter.convert(wish.getPatient()), dto.getPatientId()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(userToUserDtoConverter.convert(wish.getExecutor()), dto.getExecutorId())
        );
    }
}