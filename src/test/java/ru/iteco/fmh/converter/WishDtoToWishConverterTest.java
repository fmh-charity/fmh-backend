package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.wish.fromWishDto.WishDtoToWishConverter;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;

import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static ru.iteco.fmh.TestUtils.getWishDto;

class WishDtoToWishConverterTest {
    PatientDtoToPatientConverter dtoToPatientConverter = new PatientDtoToPatientConverter();
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    WishDtoToWishConverter convert = new WishDtoToWishConverter(dtoToPatientConverter,userDtoToUserConverter);
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        WishDto wishDto = getWishDto();
        Wish wish = convert.convert(wishDto);
        Assertions.assertAll(
                () -> assertEquals(wishDto.getId(), wish.getId()),
                () -> assertEquals(wishDto.getPatient(), patientToPatientDtoConverter.convert(wish.getPatient())),
                () -> assertEquals(wishDto.getDescription(), wish.getDescription()),
                () -> assertEquals(wishDto.getPlanExecuteDate(), wish.getPlanExecuteDate()),
                () -> assertEquals(wishDto.getStatus(), wish.getStatus()),
                () -> assertEquals(wishDto.getCreator(), userToUserDtoConverter.convert(wish.getCreator())),
                () -> assertEquals(wishDto.getExecutor(), userToUserDtoConverter.convert(wish.getExecutor()))
        );
    }
}
