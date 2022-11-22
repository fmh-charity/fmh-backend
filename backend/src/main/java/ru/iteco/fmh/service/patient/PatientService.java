package ru.iteco.fmh.service.patient;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.util.List;

public interface PatientService {
    /**
     * возвращает список всех пациентов с активной госпитализацией
     *
     * @param patientStatusList список значений для фильтра по госпитализации
     * @return список всех пациентов с любым статусом госпитализации
     */
    PatientAdmissionDto getAllPatientsByStatus(List<AdmissionsStatus> patientStatusList, int pages, int elements, boolean isActive);

    /**
     * создает новую карточку пациента
     *
     * @param patientDto информация по карточке пациента для создания
     * @return сущность
     */
    PatientDto createPatient(PatientDto patientDto);


    /**
     * бновляет информацию о пациенте
     *
     * @param patientDto информация по карточке пациента для обновления
     * @return сущность
     */
    PatientDto updatePatient(PatientDto patientDto);

    /**
     * возвращает полную инфу по конкретному пациенту
     *
     * @param id ид пациента
     * @return полная инфа по конкретному пациенту
     */
    PatientDto getPatient(Integer id);
}