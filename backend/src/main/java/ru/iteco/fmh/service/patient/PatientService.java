package ru.iteco.fmh.service.patient;

import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
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
     * @param patientCreateInfoDtoRq информация по карточке пациента для создания
     * @return сущность
     */
    PatientCreateInfoDtoRs createPatient(PatientCreateInfoDtoRq patientCreateInfoDtoRq);


    /**
     * бновляет информацию о пациенте
     * @param patientDto информация которую необходимо обновить в карточке пациента
     * @return сущность
     */
    PatientUpdateInfoDtoRs updatePatient(int id, PatientUpdateInfoDtoRq patientDto);


    /**
     * возвращает полную инфу по конкретному пациенту
     * @param id ид пациента
     * @return полная инфа по конкретному пациенту
     */
    PatientInfoDto getPatient(Integer id);

}
