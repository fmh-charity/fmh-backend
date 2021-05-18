package ru.iteco.fmh.converter.note.fromNote;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.Note;


public class NoteToNoteDtoConverter implements Converter<Note, NoteDto> {
    private final IPatientToPatientDtoConverter patientToDtoConverter;
    private final IUserToUserDtoConverter userToUserDtoConverter;

    public NoteToNoteDtoConverter(IPatientToPatientDtoConverter patientToDtoConverter,
                                  IUserToUserDtoConverter userToUserDtoConverter) {
        this.patientToDtoConverter = patientToDtoConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @Override
    public NoteDto convert(Note note) {
        NoteDto dto = new NoteDto();
        BeanUtils.copyProperties(note, dto);
        PatientDto patientDto = patientToDtoConverter.convert(note.getPatient());
        UserDto userDto = userToUserDtoConverter.convert(note.getExecutor());
        dto.setPatient(patientDto);
        dto.setExecutor(userDto);
        return dto;
    }
}
