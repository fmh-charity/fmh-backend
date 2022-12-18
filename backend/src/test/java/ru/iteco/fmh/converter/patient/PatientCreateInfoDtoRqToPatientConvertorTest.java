package ru.iteco.fmh.converter.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;
import ru.iteco.fmh.model.Room;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getAlphabeticString;
import static ru.iteco.fmh.TestUtils.getRoom;

@ExtendWith(SpringExtension.class)
class PatientCreateInfoDtoRqToPatientConvertorTest {
    @InjectMocks
    PatientCreateInfoDtoRqToPatientConvertor convertor;
    @Mock
    RoomRepository roomRepository;

    @Test
    void convertWithFactDate() {
        PatientCreateInfoDtoRq dtoRq = PatientCreateInfoDtoRq.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .dateIn(LocalDate.now())
                .dateOut(LocalDate.now())
                .dateInBoolean(true)
                .dateOutBoolean(true)
                .status(PatientStatus.ACTIVE)
                .roomId(1)
                .build();
        Room room = getRoom();
        room.setId(1);
        when(roomRepository.findByIdAndDeletedIsFalse(anyInt())).thenReturn(Optional.of(room));
        Patient patient = convertor.convert(dtoRq);
        assertAll(
                () -> assertNotNull(patient),
                () -> assertEquals(dtoRq.getFirstName(), patient.getFirstName()),
                () -> assertEquals(dtoRq.getLastName(), patient.getLastName()),
                () -> assertEquals(dtoRq.getMiddleName(), patient.getMiddleName()),
                () -> assertEquals(dtoRq.getBirthDate(), patient.getBirthDate()),
                () -> assertEquals(dtoRq.getStatus(), patient.getStatus()),
                () -> assertEquals(dtoRq.getRoomId(), patient.getRoom().getId()),
                () -> assertEquals(dtoRq.getDateIn(), patient.getFactDateIn()),
                () -> assertEquals(dtoRq.getDateOut(), patient.getFactDateOut()),
                () -> assertNull(patient.getPlanDateIn()),
                () -> assertNull(patient.getPlanDateOut())
        );
    }

    @Test
    void convertWithPlanDate() {
        PatientCreateInfoDtoRq dtoRq = PatientCreateInfoDtoRq.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .dateIn(LocalDate.now())
                .dateOut(LocalDate.now())
                .dateInBoolean(false)
                .dateOutBoolean(false)
                .status(PatientStatus.ACTIVE)
                .roomId(1)
                .build();
        Room room = getRoom();
        room.setId(1);
        when(roomRepository.findByIdAndDeletedIsFalse(anyInt())).thenReturn(Optional.of(room));
        Patient patient = convertor.convert(dtoRq);
        assertAll(
                () -> assertNotNull(patient),
                () -> assertEquals(dtoRq.getFirstName(), patient.getFirstName()),
                () -> assertEquals(dtoRq.getLastName(), patient.getLastName()),
                () -> assertEquals(dtoRq.getMiddleName(), patient.getMiddleName()),
                () -> assertEquals(dtoRq.getBirthDate(), patient.getBirthDate()),
                () -> assertEquals(dtoRq.getStatus(), patient.getStatus()),
                () -> assertEquals(dtoRq.getRoomId(), patient.getRoom().getId()),
                () -> assertEquals(dtoRq.getDateIn(), patient.getPlanDateIn()),
                () -> assertEquals(dtoRq.getDateOut(), patient.getPlanDateOut()),
                () -> assertNull(patient.getFactDateIn()),
                () -> assertNull(patient.getFactDateOut())
        );
    }
}