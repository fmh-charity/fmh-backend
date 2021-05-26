package ru.iteco.fmh.converter.note.fromNoteDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatientDto.IPatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.User;

public class NoteDtoToNoteConverter implements Converter<NoteDto, Note> {

    private final IPatientDtoToPatientConverter dtoToPatientConverter;
    private final IUserDtoToUserConverter userDtoToUserConverter;

    public NoteDtoToNoteConverter(IPatientDtoToPatientConverter dtoToPatientConverter, IUserDtoToUserConverter userDtoToUserConverter) {
        this.dtoToPatientConverter = dtoToPatientConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @Override
    public Note convert(NoteDto dto) {
        Note entity = new Note();
        BeanUtils.copyProperties(dto, entity);
        Patient patient = dtoToPatientConverter.convert(dto.getPatient());
        User creator = userDtoToUserConverter.convert(dto.getCreator());
        User executor = userDtoToUserConverter.convert(dto.getExecutor());

        entity.setPatient(patient);
        entity.setCreator(creator);
        entity.setExecutor(executor);

        return entity;
    }
}
