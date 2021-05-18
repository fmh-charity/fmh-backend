package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.iteco.fmh.converter.note.fromNoteDto.NoteDtoToNoteConverter;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.patient.fromPatientDto.IPatientDtoToPatientConverter;
import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getNoteDto;

class NoteDtoToNoteConverterTest {
    PatientDtoToPatientConverter dtoToPatientConverter = new PatientDtoToPatientConverter();
    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    NoteDtoToNoteConverter convert = new NoteDtoToNoteConverter(dtoToPatientConverter,userDtoToUserConverter);


    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();


    @Test
    void convert() {

        NoteDto noteDto = getNoteDto();
        Note note = convert.convert(noteDto);

        Assertions.assertAll(
                () -> assertEquals(noteDto.getId(), note.getId()),
                () -> assertEquals(noteDto.getPatient(), patientToPatientDtoConverter.convert(note.getPatient())),
                () -> assertEquals(noteDto.getDescription(), note.getDescription()),
                () -> assertEquals(noteDto.getComment(), note.getComment()),
                () -> assertEquals(noteDto.getPlanExecuteDate(), note.getPlanExecuteDate()),
                () -> assertEquals(noteDto.getStatus(), note.getStatus()),
                () -> assertEquals(noteDto.getCreator(), userToUserDtoConverter.convert(note.getCreator())),
                () -> assertEquals(noteDto.getExecutor(), userToUserDtoConverter.convert(note.getExecutor()))
        );
    }

}
