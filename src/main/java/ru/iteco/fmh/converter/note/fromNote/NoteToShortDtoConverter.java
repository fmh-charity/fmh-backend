package ru.iteco.fmh.converter.note.fromNote;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.note.fromNote.INoteToShortDtoConverter;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

public class NoteToShortDtoConverter implements Converter<Note, NoteShortInfoDto>, INoteToShortDtoConverter {
    @Override
    public NoteShortInfoDto convert(Note note) {
        NoteShortInfoDto dto = new NoteShortInfoDto();
        BeanUtils.copyProperties(note, dto);

        dto.setShortPatientName(note.getPatient().getShortPatientName());
//        не работает, посмотреть
//        dto.setShortExecutorName(note.getExecutor().getShortUserName());

        return dto;
    }
}
