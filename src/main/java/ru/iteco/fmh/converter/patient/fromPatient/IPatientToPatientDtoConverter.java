package ru.iteco.fmh.converter.patient.fromPatient;

import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientToPatientDtoConverter {

    PatientDto convert(Patient patient);
}
