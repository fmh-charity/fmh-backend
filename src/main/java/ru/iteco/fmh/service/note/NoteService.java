package ru.iteco.fmh.service.note;

import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;

import java.util.List;

/**
 * сервис для работы с записками
 */
public interface NoteService {
    /**
     * возвращает список всех записок co статусом active
     */
    List<NoteShortInfoDto> getAllNotes();

    NoteDto getNote(Integer id);
    /**
     * возвращает новую записку
     */
    NoteDto createNote(NoteDto noteDto);

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
}
