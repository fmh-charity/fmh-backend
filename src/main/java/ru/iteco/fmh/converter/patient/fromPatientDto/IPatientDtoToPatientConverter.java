package ru.iteco.fmh.converter.patient.fromPatientDto;

import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientDtoToPatientConverter {
    Patient convert(PatientDto patientDto);
}
