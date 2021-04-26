package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.util.List;

public interface IPatientService {

//    List<PatientDto> getAllPatients();

    List<PatientAdmissionDto> getAllPatients();

    PatientDto getPatient(Integer id);

    PatientDto createPatient(PatientDto patientDto);

    PatientDto updatePatient(PatientDto patientDto);
}
