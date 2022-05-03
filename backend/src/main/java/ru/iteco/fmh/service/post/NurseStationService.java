package ru.iteco.fmh.service.post;

import ru.iteco.fmh.dto.nurse_station.NurseStationDto;

import java.util.List;

/**
 * Сервис для работы с постами
 */

public interface NurseStationService {

    /**
     * Возвращает список всех постов
     */
    List<NurseStationDto> getAll();

    /**
     * Создание нового поста / обновление поста
     *
     * @param nurseStationDto - новый или измененный объект поста
     * @return - новый или измененный объект поста
     */
    NurseStationDto createOrUpdateNurseStation(NurseStationDto nurseStationDto);

    /**
     * Просмотр карточки поста
     *
     * @param - ID поста
     * @return - объект поста
     */
    NurseStationDto getNurseStation(int id);

    /**
     * Удаление поста
     *
     * @param id - ID удаляемого поста
     */
    void deleteNurseStation(int id);

}
