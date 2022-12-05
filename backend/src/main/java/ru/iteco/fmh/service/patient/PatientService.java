package ru.iteco.fmh.service.patient;

import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientInfoDto;

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
     * @param patientInfoDto информация по карточке пациента для создания
     * @return сущность
     */
    //PatientDto createPatient(PatientDto patientDto);

    PatientInfoDto createPatient(PatientInfoDto patientInfoDto);


    /**
     * бновляет информацию о пациенте
     * @param patientInfoDto информация по карточке пациента для обновления
     * @return сущность
     */
    //PatientDto updatePatient(PatientDto patientDto);

    PatientInfoDto updatePatient(PatientInfoDto patientInfoDto);

    /**
     * возвращает полную инфу по конкретному пациенту
     * @param id ид пациента
     * @return полная инфа по конкретному пациенту
     */
    // PatientDto getPatient(Integer id);

    PatientInfoDto getPatient(Integer id);

}
