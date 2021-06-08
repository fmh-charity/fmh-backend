package ru.iteco.fmh.service.admission;

import ru.iteco.fmh.dto.admission.AdmissionDto;

import java.util.List;

/**
 * сервис для работы с госпитализацией
 */
public interface AdmissionService {
    /**
     * возвращает полную инфу по конкретной госпитализации
     * @param admissionId ид госпитализации
     * @return полная инфа по госпитализации
     */
    AdmissionDto getAdmission(Integer admissionId);

    /**
     * создает запись о госпитализации
     * @param admissionDto информация по новой госпитализации
     * @return идентификатор новой сущности
     */
    Integer createAdmission(AdmissionDto admissionDto);

    /**
     * обновляет запись о госпитализации
     * @param admissionDto информация по новой госпитализации
     * @return обновленная сущность
     */
    AdmissionDto updateAdmission(AdmissionDto admissionDto);

    /**
     * возвращает список всех госпитализаций пациента
     * @param patientId ид пациента
     * @return список с полной инфой по всем госпитализациям пациента
     */
    List<AdmissionDto> getPatientAdmissions(Integer patientId);
}
