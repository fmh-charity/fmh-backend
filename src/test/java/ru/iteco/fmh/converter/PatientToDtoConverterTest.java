package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToDtoConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatient;

class PatientToDtoConverterTest {

    PatientToDtoConverter convertor = new PatientToDtoConverter();

    @Test
    void convert() {
        Patient patient = getPatient();

        PatientDto dto = convertor.convert(patient);

        Assertions.assertAll(
                () -> assertEquals(patient.getId(), dto.getId()),
                () -> assertEquals(patient.getFirstName(), dto.getFirstName()),
                () -> assertEquals(patient.getLastName(), dto.getLastName()),
                () -> assertEquals(patient.getMiddleName(), dto.getMiddleName()),
                () -> assertEquals(patient.getBirthday(), dto.getBirthday()),
                () -> assertEquals(patient.getShortPatientName(), dto.getShortPatientName())
        );
    }
}