package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.service.note.NoteService;

import java.util.List;

@Api(description = "Работа с записками")
@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @ApiOperation(value = "реестр всех записок со статусом active")
    @GetMapping
    public List<NoteShortInfoDto> getAllNotes() {
        return noteService.getAllNotes();
    }

    @ApiOperation(value = "Создание новой записки")
    @PostMapping
    public NoteDto createNote(@RequestBody NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @ApiOperation(value = "возвращает полную информацию по записке")
    @GetMapping("/{id}")
    public NoteDto getNote(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("id") int id
    ){
        return noteService.getNote(id);
    }

    @ApiOperation(value = "обновляет информацию по записке")
    @PatchMapping
    public NoteDto updateNote(
            @RequestBody NoteDto noteDto
    ) {
        return noteService.createNote(noteDto);
    }

    @ApiOperation(value = "формирование комментария по запискам")
    @PostMapping("/comment/{noteId}")
    public NoteDto addComment(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("noteId") int noteId,
            @RequestBody String comment
    )  {
        return noteService.addComment(noteId, comment);
    }

    @ApiOperation(value = "обработка записок по статусной модели")
    @PostMapping("/status/{noteId}")
    public NoteDto changeStatus(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("noteId") int noteId,
            @ApiParam(value = "новое значение статуса для записки", required = true) @RequestParam("status") StatusE status
            )  {
        return noteService.changeStatus(noteId, status);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
