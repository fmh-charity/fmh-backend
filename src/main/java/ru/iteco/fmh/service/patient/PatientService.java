package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.util.List;

public interface PatientService {
    // просмотр списка пациентов
    List<PatientAdmissionDto> getAllPatientsByStatus(List<String> patientStatusList);

    Integer createOrUpdatePatient(PatientDto patientDto);

    PatientDto getPatient(Integer id);

}
