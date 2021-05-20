package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.service.note.NoteService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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


    @Test
    public void addCommentShouldPassSuccess() {
        // given
        int noteId = 1;
        Note note = getNote();
        String previousComment = note.getComment();
        String newComment = "test comment";
        String expected = previousComment.concat(", ").concat(newComment);

        when(noteRepository.findById(any())).thenReturn(Optional.of(note));

        NoteDto result = sut.addComment(noteId, newComment);

        assertEquals(expected, result.getComment());
    }



}
