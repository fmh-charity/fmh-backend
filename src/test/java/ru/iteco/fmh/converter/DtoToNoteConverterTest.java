//package ru.iteco.fmh.converter;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.iteco.fmh.converter.dto.fromDto.DtoToNoteConverter;
//import ru.iteco.fmh.converter.dto.fromDto.IDtoToPatientConverter;
//import ru.iteco.fmh.dto.note.NoteDto;
//import ru.iteco.fmh.model.Note;
//import ru.iteco.fmh.model.Patient;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static ru.iteco.fmh.TestUtils.getNoteDto;
//
//class DtoToNoteConverterTest {
//    IDtoToPatientConverter dtoToPatientConverter = Mockito.mock(IDtoToPatientConverter.class);
//    DtoToNoteConverter convert = new DtoToNoteConverter(dtoToPatientConverter);
//
//    @Test
//    void convert() {
//        when(dtoToPatientConverter.convert(any())).thenReturn(new Patient());
//
//        NoteDto noteDto = getNoteDto();
//        Note note = this.convert.convert(noteDto);
//
//        Assertions.assertAll(
//                () -> assertEquals(noteDto.getDescription(), note.getDescription()),
//                () -> assertEquals(noteDto.getPlanExecuteTime(), note.getPlanExecuteTime()),
//                () -> assertEquals(noteDto.getComment(), note.getComment())
//        );
//    }
//
//}