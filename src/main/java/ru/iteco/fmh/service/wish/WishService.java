package ru.iteco.fmh.service.wish;

import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.task.StatusE;

import java.util.List;

/**
 * сервис для работы с просьбами
 */
public interface WishService {
    /**
     * возвращает список всех просьб co статусом open/in_progress
     */
    List<WishDto> getAllWishes();

    /**
     * возвращает просьбу для просмотра
     * @param id ид просьбы
     * @return просьба с полной информацией
     */
    WishDto getWish(Integer id);

    /**
     * создает новую просьбу
     * @param wishDto информация по новой просьбе
     * @return id просьбы
     */
    Integer createWish(WishDto wishDto);

    /**
     * обновляет просьбу
     * @param wishDto информация по просьбе для обновления
     * @return обновленная сущность просьбы
     */
    WishDto updateWish(WishDto wishDto);

    /**
     * возвращает список всех неисполненных просьб по пациенту
     * @param patientId ид пациента
     * @return список всех активных просьб с полной инфой по пациенту
     */
    List<WishDto> getPatientWishes(Integer patientId);

//    /**
//     * добавляет комментарий в записку и возвращает записку
//     * @param noteId ид записки
//     * @param comment комментарий для записки
//     * @return записку с добавленным комментарием
//     */
//    WishDto addComment(Integer noteId, String comment);

    /**
     * изменяет статус просьбы на - исполнен, отменен
     * @param wishId ид просьбы
     * @param status значение нового статуса для просьбы
     * @return просьбу с измененным статусом
     */
    WishDto changeStatus(Integer wishId, StatusE status);
}
