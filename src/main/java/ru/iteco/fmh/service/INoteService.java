package ru.iteco.fmh.service;

import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.dto.NoteShortInfoDto;

import java.util.List;

public interface INoteService {

    List<NoteShortInfoDto> getAllNotes();

    NoteDto getNote(Integer id);

    NoteDto createNote(NoteDto noteDto);
}
