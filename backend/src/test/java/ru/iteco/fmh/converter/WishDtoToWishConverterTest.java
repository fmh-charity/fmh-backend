package ru.iteco.fmh.converter;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.wish.WishDtoToWishConverter;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishExecutorsRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.user.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;

class WishDtoToWishConverterTest {

    UserRepository userRepository = mock(UserRepository.class);
    PatientRepository patientRepository = mock(PatientRepository.class);
    WishExecutorsRepository wishExecutorsRepository = mock(WishExecutorsRepository.class);
    WishDtoToWishConverter convert = new WishDtoToWishConverter(userRepository, patientRepository, wishExecutorsRepository);


    @Test
    @Ignore
    void convert() {
        WishDto wishDto = getWishDto();
        User user = getUser(getProfile());
        Patient patient = getPatient();

        user.setId(wishDto.getCreator().id());
        patient.setId(wishDto.getPatient().id());
        when(patientRepository.findPatientById(wishDto.getPatient().id())).thenReturn(patient);
        when(userRepository.findUserById(wishDto.getCreator().id())).thenReturn(user);
        wishDto.setWishExecutors(Collections.emptyList());

        Wish wish = convert.convert(wishDto);
        assertAll(
                () -> assertEquals(wishDto.getId(), wish.getId()),
                () -> assertEquals(wishDto.getPatient().id(), wish.getPatient().getId()),
                () -> assertEquals(wishDto.getDescription(), wish.getDescription()),
                () -> assertEquals(wishDto.getPlanExecuteDate(), wish.getPlanExecuteDate().toEpochMilli()),
                () -> assertEquals(wishDto.getCreateDate(), wish.getCreateDate().toEpochMilli()),
                () -> assertEquals(wishDto.getStatus(), wish.getStatus()),
                () -> assertEquals(wishDto.getCreator().id(), wish.getCreator().getId()),
                () -> assertEquals(wishDto.getWishExecutors().size(), wish.getExecutors().size())
        );
    }
}
