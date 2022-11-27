package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishPaginationDto;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.service.wish.WishService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Api(description = "Работа с просьбами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
@Validated
public class WishesController {

    private final WishService wishService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Secured({"Администратор", "Медицинский работник"})
    @ApiOperation(value = "реестр всех просьб")
    @GetMapping()
    public ResponseEntity<WishPaginationDto> getWishes(
            @ApiParam (required = false, name = "pages", value = "От 0")
                @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @ApiParam (required = false, name = "elements", value = "От 1 до 200")
                @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @ApiParam (required = false, name = "status", value = "[IN_PROGRESS, CANCELLED, OPEN, EXECUTED]")
                @RequestParam(name = "status", required = false) List<Status>  status,
            @ApiParam (required = false, name = "planExecuteDate", value = "Сортировка по дате исполнения")
                @RequestParam(defaultValue = "true") boolean planExecuteDate) {

        return ResponseEntity.ok(wishService.getWishes(pages, elements, status, planExecuteDate));
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех просьб со статусом open/in_progress")
    @GetMapping("/open-in-progress")
    public List<WishDto> getAllOpenInProgressWishes() {
        return wishService.getOpenInProgressWishes();
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание новой просьбы")
    @PostMapping
    public WishDto createWish(@RequestBody WishDto wishDto) {
        return wishService.createWish(wishDto);
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по просьбе")
    @GetMapping("/{id}")
    public WishDto getWish(@ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.getWish(id);
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обновляет информацию по просьбе")
    @PutMapping
    public WishDto updateWish(@RequestBody WishDto wishDto, Authentication authentication) {
        return wishService.updateWish(wishDto, authentication);
    }


    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обработка просьб по статусной модели")
    @PutMapping("{id}/status")
    public WishDto changeStatus(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id,
            @ApiParam(value = "новый статус для просьбы", required = true) @RequestParam("status") Status status,
            @ApiParam(value = "исполнитель", required = true) @RequestParam("executorId") Integer executorId,
            @ApiParam(value = "комментарий", required = true) @RequestBody WishCommentDto wishCommentDto) {
        return wishService.changeStatus(id, status, executorId, wishCommentDto);
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по комментарию просьбы")
    @GetMapping("/comments/{id}")
    public WishCommentDto getWishComment(
            @ApiParam(value = "идентификатор комментария", required = true) @PathVariable("id") int id
    ) {
        return wishService.getWishComment(id);
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех комментариев просьбы")
    @GetMapping("{id}/comments")
    public List<WishCommentDto> getAllWishComments(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id
    ) {
        return wishService.getAllWishComments(id);
    }

    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание нового комментария")
    @PostMapping("{id}/comments")
    public WishCommentDto createWishComment(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id,
            @RequestBody WishCommentDto wishCommentDto) {
        return wishService.createWishComment(id, wishCommentDto);
    }



    @Secured({"Администратор", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обновляет информацию по комментарию")
    @PutMapping("/comments")
    public WishCommentDto updateWishComment(@RequestBody WishCommentDto wishCommentDto, Authentication authentication) {
        return wishService.updateWishComment(wishCommentDto, authentication);
    }



}





