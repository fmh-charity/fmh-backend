package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.service.note.NoteService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.StatusE.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {
    @Autowired
    NoteService sut;

    @MockBean
    NoteRepository noteRepository;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Test
    public void addCommentShouldPassSuccess() {
        // given
        Note note = getNote(ACTIVE);
        Note resultNote = getNote(ACTIVE);
        String newComment = "test comment";
        String expected = "first comment".concat(", ").concat(newComment);
        resultNote.setComment(expected);
        when(noteRepository.findById(any())).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(resultNote);
        NoteDto result = sut.addComment(any(), newComment);
        assertEquals(expected, result.getComment());
    }

    @Test
    public void changeStatusShouldPassSuccess() {
        // given
        Note activeNote = getNote(ACTIVE);
        Note cancelledNote = getNote(CANCELED);
        when(noteRepository.findById(any())).thenReturn(Optional.of(activeNote));
        when(noteRepository.save(any())).thenReturn(cancelledNote);
        NoteDto result = sut.changeStatus(any(), CANCELED);
        assertEquals(CANCELED, result.getStatus());
    }

    @Test
    public void changeStatusWhenNonActiveNoteShouldThrowNoteException() {
        // given
        Note executedNote = getNote(EXECUTED);
        when(noteRepository.findById(any())).thenReturn(Optional.of(executedNote));
        assertThrows(IllegalArgumentException.class,
                () -> sut.changeStatus(any(), CANCELED));
    }

    @Test
    public void createNoteShouldPassSuccess() {
        // given
        Note note = getNote(ACTIVE);
        note.setId(7);
        NoteDto dto = factoryBean.getObject().convert(note, NoteDto.class);

        when(noteRepository.save(any())).thenReturn(note);

        Integer resultId = sut.createNote(dto);

        assertEquals(7, resultId);
    }

    @Test
    public void updateNoteShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        Note note = getNote(ACTIVE);
        NoteDto given = conversionService.convert(note, NoteDto.class);

        when(noteRepository.save(any())).thenReturn(note);

        NoteDto result = sut.updateNote(given);

        assertAll(
                () -> assertEquals(given.getId(), result.getId()),
                () -> assertEquals(given.getPatient(), result.getPatient()),
                () -> assertEquals(given.getComment(), result.getComment()),
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getExecutor(), result.getExecutor()),
                () -> assertEquals(given.getCreator(), result.getCreator())
        );
    }

    private static Note getNote(StatusE status) {
        return Note.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(LocalDateTime.now())
                .comment("first comment")
                .status(status)
                .build();
    }
}
