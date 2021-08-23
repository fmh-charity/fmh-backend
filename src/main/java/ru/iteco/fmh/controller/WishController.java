package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
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

    @ApiOperation(value = "реестр всех просьб")
    @GetMapping()
    public List<WishDto> getAllWishes() {
        return wishService.getAllWishes();
    }

    @ApiOperation(value = "реестр всех просьб со статусом open/in_progress")
    @GetMapping("/open-in-progress")
    public List<WishDto> getAllOpenInProgressWishes() {
        return wishService.getOpenInProgressWishes();
    }

    @ApiOperation(value = "Создание новой просьбы")
    @PostMapping
    public Integer createWish(@RequestBody WishDto wishDto) {
        return wishService.createWish(wishDto);
    }

    @ApiOperation(value = "возвращает полную информацию по просьбе")
    @GetMapping("/{id}")
    public WishDto getWish(@ApiParam(value = "идентификатор просьбы", required = true)@PathVariable("id") int id){
        return wishService.getWish(id);
    }
    @ApiOperation(value = "обновляет информацию по просьбе")
    @PatchMapping
    public WishDto updateWish(
            @RequestBody WishDto wishDto) {
        return wishService.updateWish(wishDto);
    }

    @ApiOperation(value = "обработка просьб по статусной модели")
    @PatchMapping("{id}/status")
    public WishDto changeStatus(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int wishId,
            @ApiParam(value = "новое значение статуса для просьбы", required = true) @RequestParam("status") StatusE status
            )  {
        return wishService.changeStatus(wishId, status);
    }

    @ApiOperation(value = "возвращает полную информацию по комментарию просьбы")
    @GetMapping("/comment/{id}")
    public WishCommentDto getWishComment(@ApiParam(value = "идентификатор комментария", required = true)@PathVariable("id") int commentId) {
        return wishService.getWishComment(commentId);
    }

    @ApiOperation(value = "реестр всех комментариев просьбы")
    @GetMapping("{id}/comment")
    public List<WishCommentDto> getAllWishComments(@ApiParam(value = "идентификатор просьбы", required = true)@PathVariable("id") int wishId) {
        return wishService.getAllWishComments(wishId);
    }

    @ApiOperation(value = "Создание нового комментария")
    @PostMapping("{id}/comment")
    public Integer createWishComment(@ApiParam(value = "идентификатор просьбы", required = true)@PathVariable("id") int wishId, @RequestBody WishCommentDto wishCommentDto) {
        return wishService.createWishComment(wishId, wishCommentDto);
    }

    @ApiOperation(value = "обновляет информацию по комментарию")
    @PatchMapping("/comment")
    public WishCommentDto updateWishComment(@RequestBody WishCommentDto wishCommentDto) {
        return wishService.updateWishComment(wishCommentDto);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
