package ru.iteco.fmh.service;

import ru.iteco.fmh.dto.AdmissionDto;

/**
 * сервис для работы с госпитализацией
 */
public interface AdmissionService {
    /**
     * возвращает полную инфу по конкретной госпитализации
     * @param id ид госпитализации
     * @return полная инфа по госпитализации
     */
    AdmissionDto getAdmissionInfo(Integer id);

    /**
     * создает запись о госпитализации
     * @param admissionDto информация по новой госпитализации
     * @return идентификатор новой сущности
     */
    Integer createAdmission(AdmissionDto admissionDto);

    /**
     * изменяет информацию по конкретной госпитализации
     * @param admissionDto обновленная информация о госпитализации
     */
    void updateAdmission(AdmissionDto admissionDto);
}
