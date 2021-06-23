package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NoteController;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getNoteDto;
import static ru.iteco.fmh.model.StatusE.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class NoteControllerTest {
    @Autowired
    NoteController sut;

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserRepository userRepository;
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
        NoteDto resultCancelled = sut.changeStatus(noteId1, EXECUTED);
        assertEquals(EXECUTED, resultCancelled.getStatus());

        // after
        Note note = noteRepository.findById(1).get();
        note.setStatus(ACTIVE);
        noteRepository.save(note);
    }

    @Test
    public void getAllActiveNotesSort() {
        List<NoteShortInfoDto> noteShortInfoDtoList = sut.getAllNotes();
        assertEquals(5, noteShortInfoDtoList.size());
        assertTrue(noteShortInfoDtoList.get(1).getPlanExecuteDate().isBefore
                (noteShortInfoDtoList.get(2).getPlanExecuteDate()));
    }

    @Test
    public void creatNoteShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        //given
        NoteDto given = getNoteDto();
        given.setCreator(conversionService.convert(userRepository.findUserById(2), UserDto.class));
        given.setExecutor(conversionService.convert(userRepository.findUserById(3), UserDto.class));
        given.getPatient().setId(2);

        Integer id = sut.createNote(given);

        assertNotNull(id);

        Note result = noteRepository.findById(id).get();

        assertAll(
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getComment(), result.getComment()),
                () -> assertEquals(given.getCreator(), conversionService.convert(result.getCreator(), UserDto.class)),
                () -> assertEquals(given.getExecutor(), conversionService.convert(result.getExecutor(), UserDto.class)),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate())
        );
        // deleting result entity
        noteRepository.deleteById(id);
    }

    @Test
    public void updateNoteShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int claimId = 3;
        NoteDto given = conversionService.convert(noteRepository.findById(claimId).get(), NoteDto.class);
        String newComment = "new comment";

        assertNotEquals(given.getComment(), newComment);

        given.setComment(newComment);

        NoteDto result = sut.updateNote(given);

        assertEquals(given.getComment(), result.getComment());
    }
}
