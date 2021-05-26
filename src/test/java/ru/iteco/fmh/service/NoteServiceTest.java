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
        Note note = getNote(StatusE.active);
        Note resultNote = getNote(StatusE.active);
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
        Note activeNote = getNote(StatusE.active);
        Note cancelledNote = getNote(StatusE.canceled);

        when(noteRepository.findById(any())).thenReturn(Optional.of(activeNote));
        when(noteRepository.save(any())).thenReturn(cancelledNote);

        NoteDto result = sut.changeStatus(any(), StatusE.canceled);

        assertEquals(StatusE.canceled, result.getStatus());
    }

    @Test
    public void changeStatusWhenNonActiveNoteShouldThrowNoteException() {
        // given
        Note executedNote = getNote(StatusE.executed);

        when(noteRepository.findById(any())).thenReturn(Optional.of(executedNote));

        assertThrows(IllegalArgumentException.class,
                () -> sut.changeStatus(any(), StatusE.canceled));
    }

    @Test
    public void createNoteShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        Note note = getNote(StatusE.active);

        when(noteRepository.save(any())).thenReturn(note);

        // result
        NoteDto expected = conversionService.convert(note, NoteDto.class);
        NoteDto result = sut.createNote(expected);

        assertEquals(expected, result);
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
