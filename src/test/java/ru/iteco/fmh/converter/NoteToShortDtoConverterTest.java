package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.note.fromNote.NoteToShortDtoConverter;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getNote;

class NoteToShortDtoConverterTest {

    NoteToShortDtoConverter convertor = new NoteToShortDtoConverter();

    @Test
    void convert() {
        Note note = getNote();

        NoteShortInfoDto shortInfoDto = convertor.convert(note);

        Assertions.assertAll(
                () -> assertEquals(note.getId(), shortInfoDto.getId()),
                () -> assertEquals(note.getPlanExecuteTime(), shortInfoDto.getPlanExecuteTime()),
                () -> assertEquals(note.getFactExecuteTime(), shortInfoDto.getFactExecuteTime()),
                () -> assertEquals(note.getPatient().getShortPatientName(), shortInfoDto.getShortPatientName()),
                () ->  assertEquals(note.getStatus(),shortInfoDto.getStatus())
        );
    }
}