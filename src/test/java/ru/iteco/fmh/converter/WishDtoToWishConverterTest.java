package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.wish.WishDtoToWishConverter;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getPatient;
import static ru.iteco.fmh.TestUtils.getUser;
import static ru.iteco.fmh.TestUtils.getWishDto;

class WishDtoToWishConverterTest {

    UserRepository userRepository = mock(UserRepository.class);
    PatientRepository patientRepository = mock(PatientRepository.class);
    WishDtoToWishConverter convert = new WishDtoToWishConverter(userRepository, patientRepository);


    @Test
    void convert() {
        WishDto wishDto = getWishDto();
        User user = getUser();
        Patient patient = getPatient();

        user.setId(wishDto.getCreatorId());
        patient.setId(wishDto.getPatientId());
        when(patientRepository.findPatientById(wishDto.getPatientId())).thenReturn(patient);
        when(userRepository.findUserById(wishDto.getCreatorId())).thenReturn(user);
        when(userRepository.findUserById(wishDto.getExecutorId())).thenReturn(null);

        Wish wish = convert.convert(wishDto);
        assertAll(
                () -> assertEquals(wishDto.getId(), wish.getId()),
                () -> assertEquals(wishDto.getPatientId(), wish.getPatient().getId()),
                () -> assertEquals(wishDto.getDescription(), wish.getDescription()),
                () -> assertEquals(wishDto.getPlanExecuteDate(), wish.getPlanExecuteDate()),
                () -> assertEquals(wishDto.getCreateDate(), wish.getCreateDate()),
                () -> assertEquals(wishDto.getFactExecuteDate(), wish.getFactExecuteDate()),
                () -> assertEquals(wishDto.getStatus(), wish.getStatus()),
                () -> assertEquals(wishDto.getCreatorId(), wish.getCreator().getId()),
                () -> assertEquals(wishDto.getExecutorId(), wish.getExecutor()),
                () -> assertNull(wishDto.getExecutorId()),
                () -> assertNull(wish.getExecutor())
        );
    }
}





