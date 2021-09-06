package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.converter.wish.WishDtoToWishConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getWishDto;

class WishDtoToWishConverterTest {
    PatientDtoToPatientConverter dtoToPatientConverter = new PatientDtoToPatientConverter();
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    WishDtoToWishConverter convert = new WishDtoToWishConverter(dtoToPatientConverter, userDtoToUserConverter);
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        WishDto wishDto = getWishDto();
        Wish wish = convert.convert(wishDto);
        assertAll(
                () -> assertEquals(wishDto.getId(), wish.getId()),
                () -> assertEquals(wishDto.getPatientId(), patientToPatientDtoConverter.convert(wish.getPatient())),
                () -> assertEquals(wishDto.getDescription(), wish.getDescription()),
                () -> assertEquals(wishDto.getPlanExecuteDate(), wish.getPlanExecuteDate()),
                () -> assertEquals(wishDto.getStatus(), wish.getStatus()),
                () -> assertEquals(wishDto.getCreatorId(), userToUserDtoConverter.convert(wish.getCreator())),
                () -> assertEquals(wishDto.getExecutorId(), userToUserDtoConverter.convert(wish.getExecutor()))
        );
    }
}
