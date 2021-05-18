package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.note.fromNote.NoteToShortDtoConverter;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getNote;

class NoteToNoteShortDtoConverterTest {

    NoteToShortDtoConverter convertor = new NoteToShortDtoConverter();

    @Test
    void convert() {
        Note note = getNote();

        NoteShortInfoDto shortInfoDto = convertor.convert(note);

        Assertions.assertAll(
                () -> assertEquals(note.getId(), shortInfoDto.getId()),
                () -> assertEquals(note.getPlanExecuteDate(), shortInfoDto.getPlanExecuteDate()),
                () -> assertEquals(note.getFactExecuteDate(), shortInfoDto.getFactExecuteDate()),
                () -> assertEquals(note.getPatient().getShortPatientName(), shortInfoDto.getShortPatientName()),
                () -> assertEquals(note.getExecutor().getShortUserName(), shortInfoDto.getShortExecutorName()),
                () ->  assertEquals(note.getStatus(),shortInfoDto.getStatus()),
                () ->  assertEquals(note.getDescription(),shortInfoDto.getDescription())
        );
    }
}