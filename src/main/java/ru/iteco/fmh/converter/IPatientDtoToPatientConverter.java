package ru.iteco.fmh.converter;

import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientDtoToPatientConverter {
    Patient convert(PatientDto patientDto);
}
