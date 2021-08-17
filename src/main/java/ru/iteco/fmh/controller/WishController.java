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
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;

@Api(description = "Работа с просьбами")
@RestController
@RequestMapping("/wish")
public class WishController {
    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @ApiOperation(value = "реестр всех просьб со статусом open/in_progress")
    @GetMapping
    public List<WishShortInfoDto> getAllWishes() {
        return wishService.getAllWishes();
    }

    @ApiOperation(value = "Создание новой просьбы")
    @PostMapping
    public Integer createWish(@RequestBody WishDto wishDto) {
        return wishService.createWish(wishDto);
    }

    @ApiOperation(value = "возвращает полную информацию по просьбе")
    @GetMapping("/{id}")
    public WishDto getWish(
            @ApiParam(value = "идентификатор просьбы", required = true)@PathVariable("id") int id){
        return wishService.getWish(id);
    }

    @ApiOperation(value = "обновляет информацию по просьбе")
    @PatchMapping
    public WishDto updateWish(
            @RequestBody WishDto wishDto) {
        return wishService.updateWish(wishDto);
    }

//    @ApiOperation(value = "формирование комментария по запискам")
//    @PostMapping("/comment/{noteId}")
//    public WishDto addComment(
//            @ApiParam(value = "идентификатор записки", required = true)@PathVariable("noteId") int noteId,
//            @RequestBody String comment)  {
//        return wishService.addComment(noteId, comment);
//    }

    @ApiOperation(value = "обработка просьб по статусной модели")
    @PatchMapping("/status/{wishId}")
    public WishDto changeStatus(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("wishId") int wishId,
            @ApiParam(value = "новое значение статуса для просьбы", required = true) @RequestParam("status") StatusE status
            )  {
        return wishService.changeStatus(wishId, status);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
