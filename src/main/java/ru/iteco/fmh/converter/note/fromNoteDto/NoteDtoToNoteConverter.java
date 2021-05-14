package ru.iteco.fmh.converter.note.fromNoteDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatientDto.IPatientDtoToPatientConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;


public class NoteDtoToNoteConverter implements Converter<NoteDto, Note> {

    private final IPatientDtoToPatientConverter dtoToPatientConverter;

    public NoteDtoToNoteConverter(IPatientDtoToPatientConverter dtoToPatientConverter) {
        this.dtoToPatientConverter = dtoToPatientConverter;
    }

    @Override
    public Note convert(NoteDto dto) {
        Note entity = new Note();
        BeanUtils.copyProperties(dto, entity);
        Patient patient = dtoToPatientConverter.convert(dto.getPatient());
        entity.setPatient(patient);

        return entity;
    }
}
