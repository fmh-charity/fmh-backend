package ru.iteco.fmh.converter;

import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientToDtoConverter {
    PatientDto convert(Patient patient);
}
