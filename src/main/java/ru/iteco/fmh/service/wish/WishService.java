package ru.iteco.fmh.service.wish;

import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.task.StatusE;

import java.util.List;

/**
 * сервис для работы с записками
 */
public interface WishService {
    /**
     * возвращает список всех записок co статусом active
     */
    List<WishShortInfoDto> getAllNotes();

    /**
     * возвращает записку для просмотра
     * @param id ид записки
     * @return записка с полной информацией
     */
    WishDto getNote(Integer id);

    /**
     * создает новую записку
     * @param wishDto информация по новой записке
     * @return id записки
     */
    Integer createNote(WishDto wishDto);

    /**
     * обновляет записку
     * @param wishDto информация по записке для обновления
     * @return обновленная сущность
     */
    WishDto updateNote(WishDto wishDto);

    /**
     * возвращает список всех неисполненных записок по пациенту
     * @param patientId ид пациента
     * @return список всех активных записок с полной инфой по пациенту
     */
    List<WishDto> getPatientNotes(Integer patientId);

    /**
     * добавляет комментарий в записку и возвращает записку
     * @param noteId ид записки
     * @param comment комментарий для записки
     * @return записку с добавленным комментарием
     */
//    WishDto addComment(Integer noteId, String comment);

    /**
     * изменяет статус записки на - исполнен, отменен
     * @param noteId ид записки
     * @param status значение нового статуса для записки
     * @return записку с измененным статусом
     */
    WishDto changeStatus(Integer noteId, StatusE status);
}
