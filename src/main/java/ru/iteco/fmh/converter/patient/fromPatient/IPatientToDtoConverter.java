package ru.iteco.fmh.converter.patient.fromPatient;

import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientToDtoConverter {
    PatientDto convert(Patient patient);
}
