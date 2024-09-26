package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatient;

@ExtendWith(SpringExtension.class)
public class PatientToPatientDtoConverterTest {
    @InjectMocks
    PatientToPatientDtoConverter patientToPatientDtoConverter;

    @Test
    void convert() {
        Patient patient = getPatient();
        PatientDto patientDto = patientToPatientDtoConverter.convert(patient);
        assertAll(
                () -> assertEquals(patient.getId(), patientDto.getId()),
                () -> assertEquals(patient.getProfile().getFirstName(), patientDto.getFirstName()),
                () -> assertEquals(patient.getProfile().getLastName(), patientDto.getLastName()),
                () -> assertEquals(patient.getProfile().getMiddleName(), patientDto.getMiddleName()),
                () -> assertEquals(patient.getProfile().getDateOfBirth(), patientDto.getBirthDate())
        );
    }
}
