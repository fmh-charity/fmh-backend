package ru.iteco.fmh.service.note;

import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.StatusE;

import java.util.List;

/**
 * сервис для работы с записками
 */
public interface NoteService {
    /**
     * возвращает список всех записок co статусом active
     */
    List<NoteShortInfoDto> getAllNotes();

    /**
     * возвращает записку для просмотра
     * @param id ид записки
     * @return записка с полной информацией
     */
    NoteDto getNote(Integer id);

    /**
     * создает новую записку
     * @param noteDto информация по новой записке
     * @return id записки
     */
    Integer createNote(NoteDto noteDto);

    /**
     * обновляет записку
     * @param noteDto информация по записке для обновления
     * @return обновленная сущность
     */
    NoteDto updateNote(NoteDto noteDto);

    /**
     * возвращает список всех неисполненных записок по пациенту
     * @param patientId ид пациента
     * @return список всех активных записок с полной инфой по пациенту
     */
    List<NoteDto> getPatientNotes(Integer patientId);

    /**
     * добавляет комментарий в записку и возвращает записку
     * @param noteId ид записки
     * @param comment комментарий для записки
     * @return записку с добавленным комментарием
     */
    NoteDto addComment(Integer noteId, String comment);

    /**
     * изменяет статус записки на - исполнен, отменен
     * @param noteId ид записки
     * @param status значение нового статуса для записки
     * @return записку с измененным статусом
     */
    NoteDto changeStatus(Integer noteId, StatusE status);
}
