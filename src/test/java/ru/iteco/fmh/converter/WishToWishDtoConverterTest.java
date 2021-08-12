package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.iteco.fmh.converter.wish.fromWish.WishToWishDtoConverter;

import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;

import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;

import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static ru.iteco.fmh.TestUtils.getNote;


class WishToWishDtoConverterTest {
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    WishToWishDtoConverter convertor = new WishToWishDtoConverter(patientToPatientDtoConverter,userToUserDtoConverter);
    @Test
    void convert() {
        Wish wish = getNote();
        WishDto dto = convertor.convert(wish);
        Assertions.assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(patientToPatientDtoConverter.convert(wish.getPatient()), dto.getPatient()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () ->  assertEquals(wish.getStatus(),dto.getStatus()),
                () ->  assertEquals(userToUserDtoConverter.convert(wish.getExecutor()),dto.getExecutor())
        );
    }
}