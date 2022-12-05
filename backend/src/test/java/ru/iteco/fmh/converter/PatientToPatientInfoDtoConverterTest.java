package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientInfoDtoConverter;
import ru.iteco.fmh.dto.patient.PatientInfoDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatient;

public class PatientToPatientInfoDtoConverterTest {
    PatientToPatientInfoDtoConverter patientToPatientInfoDtoConverter = new PatientToPatientInfoDtoConverter();

    @Test
    void convert() {
        Patient patient = getPatient();
        PatientInfoDto patientDto = patientToPatientInfoDtoConverter.convert(patient);
        assertAll(
                () -> assertEquals(patient.getId(), patientDto.getId()),
                () -> assertEquals(patient.getFirstName(), patientDto.getFirstName()),
                () -> assertEquals(patient.getLastName(), patientDto.getLastName()),
                () -> assertEquals(patient.getMiddleName(), patientDto.getMiddleName()),
                () -> assertEquals(patient.getBirthDate(), patientDto.getBirthDate())
        );
    }
}
