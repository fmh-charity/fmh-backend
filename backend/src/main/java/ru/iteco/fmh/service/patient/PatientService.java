package ru.iteco.fmh.service.patient;

import org.springframework.data.domain.Pageable;
import ru.iteco.fmh.dto.PageDto;
import ru.iteco.fmh.dto.patient.*;

import java.util.List;

public interface PatientService {

    /**
     * Постраничный поиск по пациентам.
     *
     * @param pageable          атрибуты для страницы
     * @param searchPositionDto атрибуты для поиска пациентов.
     * @return {@link PageDto} со списком пациентов.
     */
    PageDto<PatientByStatusRs> getPage(Pageable pageable, SearchPatientsDto searchPositionDto);

    /**
     * возвращает список всех пациентов с активной госпитализацией
     * @param patientStatusList список значений для фильтра по госпитализации
     * @return список всех пациентов с активной госпитализацией
     */
    List<PatientByStatusRs> getAllPatientsByStatus(List<String> patientStatusList);

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
