package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.NoteController;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.dto.patient.PatientDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getNoteDto;
import static ru.iteco.fmh.TestUtils.getPatientDto;


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

    @Test
    public void creatNoteShouldPassSuccess() {
        //given
        NoteDto given = getNoteDto();

        UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
        given.setCreator(userToUserDtoConverter.convert(userRepository.findUserById(2)));
        given.setExecutor(userToUserDtoConverter.convert(userRepository.findUserById(3)));
        given.getPatient().setId(2);

        NoteDto result = sut.createNote(given);
        given.setId(result.getId());

        assertAll(
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getComment(), result.getComment()),
                () -> assertEquals(given.getId(), result.getId()),
                () -> assertEquals(given.getCreator(), result.getCreator()),
                () -> assertEquals(given.getExecutor(), result.getExecutor()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate())
        );

        // deleting result entity
        noteRepository.deleteById(result.getId());
    }
}
