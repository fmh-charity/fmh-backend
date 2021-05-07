package ru.iteco.fmh.converter.dto.fromDto;

import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IDtoToPatientConverter {
    Patient convert(PatientDto dto);
}
