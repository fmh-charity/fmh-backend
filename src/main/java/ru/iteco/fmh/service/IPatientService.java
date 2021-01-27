package ru.iteco.fmh.service;

import ru.iteco.fmh.dto.PatientDto;

import java.util.List;

public interface IPatientService {

    List<PatientDto> getAllPatients();

    PatientDto getPatient(Integer id);

    PatientDto createPatient(PatientDto patientDto);

    PatientDto updatePatient(PatientDto patientDto);
}
