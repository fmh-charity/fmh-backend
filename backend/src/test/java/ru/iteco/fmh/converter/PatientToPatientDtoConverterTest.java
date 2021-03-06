package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatient;

public class PatientToPatientDtoConverterTest {
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();

    @Test
    void convert() {
        Patient patient = getPatient();
        PatientDto patientDto = patientToPatientDtoConverter.convert(patient);
        assertAll(
                () -> assertEquals(patient.getId(), patientDto.getId()),
                () -> assertEquals(patient.getFirstName(), patientDto.getFirstName()),
                () -> assertEquals(patient.getLastName(), patientDto.getLastName()),
                () -> assertEquals(patient.getMiddleName(), patientDto.getMiddleName()),
                () -> assertEquals(patient.getBirthDate().toEpochMilli(), patientDto.getBirthDate())
        );
    }
}
