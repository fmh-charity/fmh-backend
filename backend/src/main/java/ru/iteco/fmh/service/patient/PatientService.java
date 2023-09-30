package ru.iteco.fmh.service.patient;

import org.springframework.data.domain.PageRequest;
import ru.iteco.fmh.dto.patient.PatientByStatusRs;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;

import java.util.List;

public interface PatientService {
    /**
     * возвращает список всех пациентов с активной госпитализацией
     * @param search строка для фильтра
     * @return список всех пациентов
     */
    List<PatientByStatusRs> getAllPatientsByStatus(PageRequest pageRequest, String search);

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
    PatientDto getPatient(Integer id);


    /**
     * Удаление пациента
     * @param id ид пациента
     */
    void deletePatient(int id);
}
