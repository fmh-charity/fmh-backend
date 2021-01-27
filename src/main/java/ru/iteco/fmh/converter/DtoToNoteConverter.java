package ru.iteco.fmh.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;


public class DtoToNoteConverter implements Converter<NoteDto, Note> {

    private final IDtoToPatientConverter dtoToPatientConverter;

    public DtoToNoteConverter(IDtoToPatientConverter dtoToPatientConverter) {
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
