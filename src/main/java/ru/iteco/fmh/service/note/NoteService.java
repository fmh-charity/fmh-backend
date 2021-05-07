package ru.iteco.fmh.service.note;

import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;

import java.util.List;

/**
 * сервис для работы с записками
 */
public interface NoteService {
    List<NoteShortInfoDto> getAllNotes();

    NoteDto getNote(Integer id);

    NoteDto createNote(NoteDto noteDto);

    /**
     * возвращает список всех неисполненных записок по пациенту
     * @param patientId ид пациента
     * @return список всех активных записок с полной инфой по пациенту
     */
    List<NoteDto> getPatientNotes(Integer patientId);
}
