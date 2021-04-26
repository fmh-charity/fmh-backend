package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.dto.fromDto.DtoToPatientConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatientDto;

class DtoToPatientConverterTest {
    DtoToPatientConverter dto = new DtoToPatientConverter();

    @Test
    void convert() {
        PatientDto patientDto = getPatientDto();

        Patient patient = dto.convert(patientDto);

        Assertions.assertAll(
                () -> assertEquals(patientDto.getId(), patient.getId()),
                () -> assertEquals(patientDto.getFirstName(), patient.getFirstName()),
                () -> assertEquals(patientDto.getLastName(), patient.getLastName()),
                () -> assertEquals(patientDto.getMiddleName(), patient.getMiddleName()),
                () -> assertEquals(patientDto.getBirthday(), patient.getBirthday()),
                () -> assertEquals(patientDto.getShortPatientName(), patient.getShortPatientName())

        );
    }


}