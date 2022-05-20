package ru.iteco.fmh.service.room;

import ru.iteco.fmh.dto.room.RoomDto;
import ru.iteco.fmh.dto.room.RoomDtoRq;
import ru.iteco.fmh.dto.room.RoomDtoRs;
import ru.iteco.fmh.model.Room;

import java.util.List;


/**
 * Сервис для работы с палатами
 */

public interface RoomService {

    /**
     *  Получить список всех палат
     */
    List<RoomDto> getAllRooms();

    /**
     * Создание палаты
     *
     * @param roomDto - объект создаваемой / редактируемой палаты
     * @return - объект созданной / отредактированной палаты
     */
    RoomDtoRs createOrUpdateRoom(int id, RoomDtoRq roomDto);

    /**
     * Просмотр карточки палаты
     *
     * @param id - ИД палаты
     * @return - объект палаты
     */
    RoomDto getRoom(int id);

    /**
     * Удаление палаты
     *
     * @param id - ИД удаляемой палаты
     */
    void deleteRoom(int id);

}
