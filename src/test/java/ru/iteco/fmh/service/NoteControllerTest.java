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

        NoteDto noteDto = sut.addComment(noteId, givenComment);

        assertEquals(expected, noteDto.getComment());
    }

    @Test
    public void getAllActiveNotesSort() {

        List<NoteShortInfoDto> noteShortInfoDtoList = sut.getAllNotes();
        assertEquals(5, noteShortInfoDtoList.size());
        assertTrue(noteShortInfoDtoList.get(1).getPlanExecuteDate().isBefore
                (noteShortInfoDtoList.get(2).getPlanExecuteDate()));
    }
}
