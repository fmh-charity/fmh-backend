package ru.iteco.fmh.service.admission;

import ru.iteco.fmh.dto.admission.AdmissionDto;

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
     * создает или обновляет запись о госпитализации
     * @param admissionDto информация по новой госпитализации
     * @return идентификатор новой сущности
     */
    Integer createOrUpdateAdmission(AdmissionDto admissionDto);


}
