package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.PatientToPatientAdmissionDtoConverter;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.Patient;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getPatient;

public class PatientToPatientAdmissionDtoConverterTest {
    PatientToPatientAdmissionDtoConverter converter = new PatientToPatientAdmissionDtoConverter();

    @Test
    void convert() {
        Patient patient = getPatient();
        patient.getCurrentAdmission().setPlanDateIn(Instant.now());
        PatientAdmissionDto patientAdmissionDto = converter.convert(patient);

        assertAll(
                () -> assertEquals(patient.getId(), patientAdmissionDto.getId()),
                () -> assertEquals(patient.getFirstName(), patientAdmissionDto.getFirstName()),
                () -> assertEquals(patient.getLastName(), patientAdmissionDto.getLastName()),
                () -> assertEquals(patient.getMiddleName(), patientAdmissionDto.getMiddleName()),
                () -> assertEquals(patient.getBirthDate(), patientAdmissionDto.getBirthday()),
                () -> assertEquals(patient.getCurrentAdmission().getStatus(), patientAdmissionDto.getAdmissionsStatus()),
                () -> assertEquals(patient.getCurrentAdmission().getPlanDateIn().toEpochMilli(), patientAdmissionDto.getPlanDateIn()),
                () -> assertNull(patient.getCurrentAdmission().getFactDateOut()),
                () -> assertNull(patientAdmissionDto.getFactDateOut()),
                () -> assertNull(patient.getCurrentAdmission().getPlanDateOut()),
                () -> assertNull(patientAdmissionDto.getPlanDateOut())

        );
    }
}


