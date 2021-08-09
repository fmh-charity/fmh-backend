package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;

@Api(description = "Работа с записками")
@RestController
@RequestMapping("/note")
public class WishController {
    private final WishService wishService;
    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @ApiOperation(value = "реестр всех записок со статусом active")
    @GetMapping
    public List<WishShortInfoDto> getAllNotes() {
        return wishService.getAllNotes();
    }

    @ApiOperation(value = "Создание новой записки")
    @PostMapping
    public Integer createNote(@RequestBody WishDto wishDto) {
        return wishService.createNote(wishDto);
    }

    @ApiOperation(value = "возвращает полную информацию по записке")
    @GetMapping("/{id}")
    public WishDto getNote(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("id") int id){
        return wishService.getNote(id);
    }

    @ApiOperation(value = "обновляет информацию по записке")
    @PatchMapping
    public WishDto updateNote(
            @RequestBody WishDto wishDto) {
        return wishService.updateNote(wishDto);
    }

//    @ApiOperation(value = "формирование комментария по запискам")
//    @PostMapping("/comment/{noteId}")
//    public WishDto addComment(
//            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("noteId") int noteId,
//            @RequestBody String comment)  {
//        return wishService.addComment(noteId, comment);
//    }

    @ApiOperation(value = "обработка записок по статусной модели")
    @PatchMapping("/status/{noteId}")
    public WishDto changeStatus(
            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("noteId") int noteId,
            @ApiParam(value = "новое значение статуса для записки", required = true) @RequestParam("status") StatusE status
            )  {
        return wishService.changeStatus(noteId, status);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
