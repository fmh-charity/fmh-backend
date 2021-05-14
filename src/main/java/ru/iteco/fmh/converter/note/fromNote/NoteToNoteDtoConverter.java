package ru.iteco.fmh.converter.note.fromNote;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Note;

//не х
public class NoteToNoteDtoConverter implements Converter<Note, NoteDto> {
    private final IPatientToPatientDtoConverter patientToDtoConverter;

    public NoteToNoteDtoConverter(IPatientToPatientDtoConverter patientToDtoConverter) {
        this.patientToDtoConverter = patientToDtoConverter;
    }

    @Override
    public NoteDto convert(Note note) {
        NoteDto dto = new NoteDto();
        BeanUtils.copyProperties(note, dto);
        PatientDto patientDto = patientToDtoConverter.convert(note.getPatient());
        dto.setPatient(patientDto);
        return dto;
    }
}
