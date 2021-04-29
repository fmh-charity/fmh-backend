package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.util.List;

public interface IPatientService {

//    List<PatientDto> getAllPatients();

//    List<PatientAdmissionDto> getAllPatients();

    // просмотр списка пациентов
    List<PatientAdmissionDto> getAllPatientsByStatus(List<String> status);

    PatientDto createPatient(PatientDto patientDto);

    PatientDto getPatient(Integer id);

    PatientDto updatePatient(PatientDto patientDto);
}
