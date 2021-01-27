package ru.iteco.fmh.converter;

import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientToPatientDtoConverter {
    PatientDto convert(Patient patient);
}
