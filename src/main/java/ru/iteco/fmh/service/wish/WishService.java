package ru.iteco.fmh.service.wish;

import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.StatusE;

import java.util.List;

/**
 * сервис для работы с просьбами
 */
public interface WishService {
    /**
     * возвращает список всех просьб
     */
    List<WishDto> getAllWishes();

    /**
     * возвращает список всех просьб co статусом open/in_progress
     */
    List<WishDto> getOpenInProgressWishes();

    /**
     * возвращает просьбу для просмотра
     * @param id ид просьбы
     * @return просьба с полной информацией
     */
    WishDto getWish(int id);

    /**
     * создает новую просьбу
     * @param wishDto информация по новой просьбе
     * @return id просьбы
     */
    int createWish(WishDto wishDto);

    /**
     * обновляет просьбу
     * @param wishDto информация по просьбе для обновления
     * @return обновленная сущность просьбы
     */
    WishDto updateWish(WishDto wishDto);

    /**
     * возвращает список всех просьб по пациенту
     * @param patientId ид пациента
     * @return список всех просьб по пациенту
     */
    List<WishDto> getPatientAllWishes(int patientId);

    /**
     * возвращает список всех просьб по пациенту co статусом open/in_progress
     * @param patientId ид пациента
     * @return список всех просьб по пациенту co статусом open/in_progress
     */
    List<WishDto> getPatientOpenInProgressWishes(int patientId);

    /**
     * изменяет статус просьбы - обработка документа “Просьба” по статусной модели
     * @param wishId ид просьбы
     * @param status значение нового статуса для просьбы
     * @return просьбу с измененным статусом
     */
    WishDto changeStatus(int wishId, StatusE status);


    /**
     * возвращает комментарий для просмотра
     *
     * @param commentId ид комментария
     * @return комментарий с полной информацией
     */
    WishCommentDto getWishComment(int commentId);

    /**
     * возвращает все комментарии просьбы для просмотра
     * @param wishId ид просьбы
     * @return список всех комментариев по просьбе
     */
    List<WishCommentDto> getAllWishComments(int wishId);

    /**
     * создает новый комментарий просьбы
     * @param wishId ид просьбы
     * @param wishCommentDto информация по комментарию
     * @return id комментария
     */
    int createWishComment(int wishId, WishCommentDto wishCommentDto);


    /**
     * обновляет комментарий просьбы
     * @param wishCommentDto информация по комментарию для обновления
     * @return обновленная сущность комментария
     */
    WishCommentDto updateWishComment(WishCommentDto wishCommentDto);
}
