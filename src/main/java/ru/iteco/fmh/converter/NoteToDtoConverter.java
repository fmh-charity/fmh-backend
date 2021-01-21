package ru.iteco.fmh.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Note;


public class NoteToDtoConverter implements Converter<Note, NoteDto> {
    private final IPatientToDtoConverter patientToDtoConverter;

    public NoteToDtoConverter(IPatientToDtoConverter patientToDtoConverter) {
        this.patientToDtoConverter = patientToDtoConverter;
    }

    @Override
    public NoteDto convert(Note note) {
        NoteDto dto = new NoteDto();
        BeanUtils.copyProperties(note, dto);
        // TODO: 18.01.2021  добавить конверторы
        PatientDto patientDto = patientToDtoConverter.convert(note.getPatient());
        dto.setPatient(patientDto);

        return dto;
    }

}
