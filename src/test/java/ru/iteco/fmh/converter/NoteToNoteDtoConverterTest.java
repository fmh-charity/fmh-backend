package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ru.iteco.fmh.converter.note.fromNote.NoteToNoteDtoConverter;

import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;

import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.note.NoteDto;

import ru.iteco.fmh.model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;


import static ru.iteco.fmh.TestUtils.getNote;


class NoteToNoteDtoConverterTest {
    PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    NoteToNoteDtoConverter convertor = new NoteToNoteDtoConverter(patientToPatientDtoConverter,userToUserDtoConverter);
    @Test
    void convert() {
        Note note = getNote();
        NoteDto dto = convertor.convert(note);
        Assertions.assertAll(
                () -> assertEquals(note.getId(), dto.getId()),
                () -> assertEquals(patientToPatientDtoConverter.convert(note.getPatient()), dto.getPatient()),
                () -> assertEquals(note.getDescription(), dto.getDescription()),
                () -> assertEquals(note.getComment(), dto.getComment()),
                () -> assertEquals(note.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () ->  assertEquals(note.getStatus(),dto.getStatus()),
                () ->  assertEquals(userToUserDtoConverter.convert(note.getExecutor()),dto.getExecutor())
        );
    }
}