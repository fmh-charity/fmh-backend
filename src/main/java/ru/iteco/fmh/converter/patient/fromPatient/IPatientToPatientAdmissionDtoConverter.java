package ru.iteco.fmh.converter.patient.fromPatient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;

import ru.iteco.fmh.model.Patient;

public interface IPatientToPatientAdmissionDtoConverter {

    PatientAdmissionDto convert (Patient patient);
}
