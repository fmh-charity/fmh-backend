package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientInfoDtoToPatientConverter;
import ru.iteco.fmh.dto.patient.PatientInfoDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static ru.iteco.fmh.TestUtils.getPatientInfoDto;

class PatientInfoDtoToPatientConverterTest {

    PatientInfoDtoToPatientConverter dto = new PatientInfoDtoToPatientConverter();

    @Test
    void convert() {
        // given
        PatientInfoDto patientInfoDto = getPatientInfoDto();
        Patient patient = dto.convert(patientInfoDto);

        assertAll(
                () -> assertEquals(patientInfoDto.getId(), patient.getId()),
                () -> assertEquals(patientInfoDto.getFirstName(), patient.getFirstName()),
                () -> assertEquals(patientInfoDto.getLastName(), patient.getLastName()),
                () -> assertEquals(patientInfoDto.getMiddleName(), patient.getMiddleName()),
                () -> assertEquals(patientInfoDto.getBirthDate(), patient.getBirthDate())
        );
    }
}