package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.iteco.fmh.converter.note.NoteToDtoConverter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToDtoConverter;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getNote;
import static ru.iteco.fmh.TestUtils.getPatientDto;

class NoteToDtoConverterTest {

    IPatientToDtoConverter patientToDtoConverter = Mockito.mock(IPatientToDtoConverter.class);
    NoteToDtoConverter convertor = new NoteToDtoConverter(patientToDtoConverter);

    @Test
    void convert() {
        when(patientToDtoConverter.convert(any())).thenReturn(getPatientDto());
        Note note = getNote();

        NoteDto dto = convertor.convert(note);

        Assertions.assertAll(
                () -> assertEquals(note.getId(), dto.getId()),
                () -> assertEquals(note.getDescription(), dto.getDescription()),
                () -> assertEquals(note.getComment(), dto.getComment()),
                () -> assertEquals(note.getPlanExecuteTime(), dto.getPlanExecuteTime()),
                () ->  assertEquals(note.getStatus(),dto.getStatus())
        );
    }
}