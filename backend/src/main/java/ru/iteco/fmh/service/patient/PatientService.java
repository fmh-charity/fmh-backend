package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.util.List;

public interface PatientService {
    /**
     * возвращает список всех пациентов с активной госпитализацией
     * @param patientStatusList список значений для фильтра по госпитализации
     * @return список всех пациентов с активной госпитализацией
     */
    List<PatientAdmissionDto> getAllPatientsByStatus(List<String> patientStatusList);

    /**
     * создает новую карточку пациента
     * @param patientDto информация по карточке пациента для создания
     * @return сущность
     */
    PatientAdmissionDto createPatient(PatientAdmissionDto patientDto);


    /**
     * бновляет информацию о пациенте
     * @param patientDto информация по карточке пациента для обновления
     * @return сущность
     */
    PatientAdmissionDto updatePatient(PatientAdmissionDto patientDto);

    /**
     * возвращает полную инфу по конкретному пациенту
     * @param id ид пациента
     * @return полная инфа по конкретному пациенту
     */
    PatientDto getPatient(Integer id);

}
