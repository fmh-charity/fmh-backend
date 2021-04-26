package ru.iteco.fmh.service.note;

import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;

import java.util.List;

public interface INoteService {

    List<NoteShortInfoDto> getAllNotes();

    NoteDto getNote(Integer id);

    NoteDto createNote(NoteDto noteDto);
}
