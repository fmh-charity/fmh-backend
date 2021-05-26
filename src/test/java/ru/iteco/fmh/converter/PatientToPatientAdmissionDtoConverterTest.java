package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientAdmissionDtoConverter;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getPatient;

public class PatientToPatientAdmissionDtoConverterTest {
    PatientToPatientAdmissionDtoConverter converter = new PatientToPatientAdmissionDtoConverter();
    @Test
    void convert() {
        Patient patient = getPatient();
        PatientAdmissionDto patientAdmissionDto = converter.convert(patient);
        Assertions.assertAll(
                () -> assertEquals(patient.getId(), patientAdmissionDto.getId()),
                () -> assertEquals(patient.getFirstName(), patientAdmissionDto.getFirstName()),
                () -> assertEquals(patient.getLastName(), patientAdmissionDto.getLastName()),
                () -> assertEquals(patient.getMiddleName(), patientAdmissionDto.getMiddleName()),
                () -> assertEquals(patient.getBirthDate(), patientAdmissionDto.getBirthday()),
                () -> assertEquals(patient.getCurrentAdmission().getStatus(), patientAdmissionDto.getAdmissionsStatus()),
                () -> assertEquals(patient.getCurrentAdmission().getFactDateIn(), patientAdmissionDto.getFactDateIn()),
                () -> assertEquals(patient.getCurrentAdmission().getFactDateOut(), patientAdmissionDto.getFactDateOut()),
                () -> assertEquals(patient.getCurrentAdmission().getPlanDateIn(), patientAdmissionDto.getPlanDateIn()),
                () -> assertEquals(patient.getCurrentAdmission().getPlanDateOut(), patientAdmissionDto.getPlanDateOut())
        );
    }
}


