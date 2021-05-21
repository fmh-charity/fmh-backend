package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NoteController;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class NoteControllerTest {
    @Autowired
    NoteController sut;

    @Autowired
    NoteRepository noteRepository;
    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Test
    public void addCommentShouldPassSuccess() {
        // given
        int noteId = 1;
        String givenComment = "test comment";
        String expected = "note1-comment, test comment";

        NoteDto result = sut.addComment(noteId, givenComment);

        assertEquals(expected, result.getComment());
    }

    @Test
    public void changeStatusShouldPassSuccess() {
        // given
        int noteId1 = 1;
        int noteId4 = 4;

        NoteDto resultCancelled = sut.changeStatus(noteId1, StatusE.canceled);
        NoteDto resultExecuted = sut.changeStatus(noteId4, StatusE.canceled);

        assertAll(
                () -> assertEquals(StatusE.canceled, resultCancelled.getStatus()),
                () -> assertEquals(StatusE.executed, resultExecuted.getStatus())
        );

        // after
        Note note = noteRepository.findById(1).get();
        note.setStatus(StatusE.active);
        noteRepository.save(note);
    }

    @Test
    public void getAllActiveNotesSort() {

        List<NoteShortInfoDto> noteShortInfoDtoList = sut.getAllNotes();
        assertEquals(5, noteShortInfoDtoList.size());
        assertTrue(noteShortInfoDtoList.get(1).getPlanExecuteDate().isBefore
                (noteShortInfoDtoList.get(2).getPlanExecuteDate()));
    }
}
