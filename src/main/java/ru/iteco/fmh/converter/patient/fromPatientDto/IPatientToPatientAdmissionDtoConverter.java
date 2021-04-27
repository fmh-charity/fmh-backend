package ru.iteco.fmh.converter.patient.fromPatientDto;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

public interface IPatientToPatientAdmissionDtoConverter {

    PatientAdmissionDto convert (Patient patient);
}
