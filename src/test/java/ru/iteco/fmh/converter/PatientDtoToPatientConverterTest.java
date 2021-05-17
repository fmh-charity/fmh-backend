package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatientDto;

class PatientDtoToPatientConverterTest {
    PatientDtoToPatientConverter dto = new  PatientDtoToPatientConverter();

    @Test
    void convert() {
        PatientDto patientDto = getPatientDto();

        Patient patient = dto.convert(patientDto);

        Assertions.assertAll(
                () -> assertEquals(patientDto.getId(), patient.getId()),
                () -> assertEquals(patientDto.getFirstName(), patient.getFirstName()),
                () -> assertEquals(patientDto.getLastName(), patient.getLastName()),
                () -> assertEquals(patientDto.getMiddleName(), patient.getMiddleName()),
                () -> assertEquals(patientDto.getBirthDate(), patient.getBirthDate()),
                () -> assertEquals(patientDto.getShortPatientName(), patient.getShortPatientName())
        );
    }


}