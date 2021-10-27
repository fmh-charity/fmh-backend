package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.UserRoleRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.UserPrinciple;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;

@Api(description = "Работа с просьбами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
public class WishesController {

    private final WishService wishService;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех просьб")
    @GetMapping()
    public List<WishDto> getAllWishes() {
        return wishService.getAllWishes();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех просьб со статусом open/in_progress")
    @GetMapping("/open-in-progress")
    public List<WishDto> getAllOpenInProgressWishes() {
        return wishService.getOpenInProgressWishes();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание новой просьбы")
    @PostMapping
    public WishDto createWish(@RequestBody WishDto wishDto) {
        return wishService.createWish(wishDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по просьбе")
    @GetMapping("/{id}")
    public WishDto getWish(@ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.getWish(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обновляет информацию по просьбе")
    @PutMapping
    public WishDto updateWish(@RequestBody WishDto wishDto, UserPrinciple userPrinciple) {
        User userCreator = userRepository.findUserById(wishDto.getCreatorId());
        checkRole(userCreator, userPrinciple);
        return wishService.updateWish(wishDto);
    }


    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обработка просьб по статусной модели")
    @PutMapping("{id}/status")
    public WishDto changeStatus(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id,
            @ApiParam(value = "новый статус для просьбы", required = true) @RequestParam("status") Status status,
            @ApiParam(value = "исполнитель", required = true) @RequestParam("executorId") Integer executorId,
            @ApiParam(value = "комментарий", required = true) @RequestBody WishCommentDto wishCommentDto) {
        return wishService.changeStatus(id, status, executorId, wishCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по комментарию просьбы")
    @GetMapping("/comments/{id}")
    public WishCommentDto getWishComment(
            @ApiParam(value = "идентификатор комментария", required = true) @PathVariable("id") int id
    ) {
        return wishService.getWishComment(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех комментариев просьбы")
    @GetMapping("{id}/comments")
    public List<WishCommentDto> getAllWishComments(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id
    ) {
        return wishService.getAllWishComments(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание нового комментария")
    @PostMapping("{id}/comments")
    public WishCommentDto createWishComment(
            @ApiParam(value = "идентификатор просьбы", required = true) @PathVariable("id") int id,
            @RequestBody WishCommentDto wishCommentDto) {
        return wishService.createWishComment(id, wishCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "обновляет информацию по комментарию")
    @PutMapping("/comments")
    public WishCommentDto updateWishComment(@RequestBody WishCommentDto wishCommentDto, UserPrinciple userPrinciple) {
        User userCreator = userRepository.findUserById(wishCommentDto.getCreatorId());
        checkRole(userCreator, userPrinciple);
        return wishService.updateWishComment(wishCommentDto);
    }


    public void checkRole(User userCreator, UserPrinciple userPrinciple) {
        String roleName = userRoleRepository.findUserRoleByUser(userCreator).getRole().getName();
        if (userCreator.getId() != userPrinciple.getId() && !roleName.equals("ROLE_ADMINISTRATOR")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа!");
        }
    }
}





