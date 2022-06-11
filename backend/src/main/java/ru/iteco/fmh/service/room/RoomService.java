package ru.iteco.fmh.service.room;

import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;

import java.util.List;


/**
 * Сервис для работы с палатами
 */

public interface RoomService {

    /**
     *  Получить список всех палат
     */
    List<RoomDtoRs> getAllRooms();

    /**
     * Создание палаты
     *
     * @param roomDto - объект создаваемой
     * @return - объект созданной
     */
    RoomDtoRs createRoom(RoomDtoRq roomDto);

    /**
     * Редактирование палаты
     *
     * @param roomDto - объект редактируемой
     * @return - объект отредактированной палаты
     */
    RoomDtoRs updateRoom(int id, RoomDtoRq roomDto);

    /**
     * Просмотр карточки палаты
     *
     * @param id - ИД палаты
     * @return - объект палаты
     */
    RoomDtoRs getRoom(int id);

    /**
     * Удаление палаты
     *
     * @param id - ИД удаляемой палаты
     */
    void deleteRoom(int id);

}
