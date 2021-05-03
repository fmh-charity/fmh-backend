package ru.iteco.fmh.converter.note;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

public class NoteToShortDtoConverter implements Converter<Note, NoteShortInfoDto>, INoteToShortDtoConverter {
    @Override
    public NoteShortInfoDto convert(Note note) {
        NoteShortInfoDto dto = new NoteShortInfoDto();
        BeanUtils.copyProperties(note, dto);

//        TODO: 27.04.2021 delete, shortPatientName not actual?
//        dto.setShortPatientName(note.getPatient().getShortPatientName());

        // TODO: 19.01.2021 добавить исполнителя
        return dto;
    }
}
