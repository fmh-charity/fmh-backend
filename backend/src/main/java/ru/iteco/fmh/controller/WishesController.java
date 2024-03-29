package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.dto.wish.WishCreationRequest;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishPaginationDto;
import ru.iteco.fmh.dto.wish.WishUpdateRequest;
import ru.iteco.fmh.dto.wish.WishVisibilityDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.service.wish.WishService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Работа с просьбами")
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
@Validated
public class WishesController {

    private final WishService wishService;
    private final List<String> fields = List.of("id", "status");
    private final List<String> directions = List.of("asc", "desc");

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех просьб")
    @GetMapping
    public ResponseEntity<WishPaginationDto> getWishes(
            @Parameter(name = "pages", description = "От 0")
            @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @Parameter(name = "elements", description = "От 1 до 200")
            @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @Parameter(name = "searchValue", description = "Строка для поиска")
            @RequestParam(required = false, defaultValue = "") String searchValue,
            @Parameter(name = "sortField", description = "Параметр по которому будет осуществляться сортировка. "
                    + "Допустимые поля: id, status")
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @Parameter(name = "sortDirection", description = "Направление сортировки, допустимые значения asc, desc")
            @RequestParam(defaultValue = "ASC", required = false) String sortDirection
    ) {
        sortDirection = sortDirection.toLowerCase();
        if (!directions.contains(sortDirection)) {
            throw new IncorrectDataException("Неверное значение в поле направления сортировки");
        }
        if (!fields.contains(sortField)) {
            throw new IncorrectDataException("Неверное значение в поле сортировки");
        }
        return ResponseEntity.ok(wishService.getWishes(pages, elements, searchValue, sortField, sortDirection));
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех просьб со статусом open/in_progress")
    @GetMapping("/open-in-progress")
    public List<WishDto> getAllOpenInProgressWishes() {
        return wishService.getOpenInProgressWishes();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Создание новой просьбы")
    @PostMapping
    public WishDto createWish(@RequestBody WishCreationRequest wishCreationRequest) {
        return wishService.createWish(wishCreationRequest);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информацию по просьбе")
    @GetMapping("/{id}")
    public WishDto getWish(@Parameter(description = "идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.getWish(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Обновление информации по просьбе")
    @PutMapping("/{id}")
    public WishDto updateWish(@RequestBody WishUpdateRequest wishUpdateRequest, Authentication authentication, @PathVariable("id") int id) {
        return wishService.updateWish(wishUpdateRequest, authentication, id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Обработка просьб по статусной модели")
    @PutMapping("{id}/status")
    public WishDto changeStatus(
            @Parameter(name = "идентификатор просьбы", required = true) @PathVariable("id") int id,
            @Parameter(name = "новый статус для просьбы", required = true) @RequestParam("status") Status status,
            @Parameter(name = "исполнитель", required = true) @RequestParam("executorId") Integer executorId,
            @Parameter(name = "комментарий", required = true) @RequestBody WishCommentDto wishCommentDto) {
        return wishService.changeStatus(id, status, executorId, wishCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информацию по комментарию просьбы")
    @GetMapping("comments/{id}")
    public WishCommentInfoDto getWishComment(@Parameter(description = "Идентификатор комментария", required = true)
                                             @PathVariable("id") int id) {
        return wishService.getWishComment(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех комментариев просьбы")
    @GetMapping("{id}/comments")
    public List<WishCommentInfoDto> getAllWishComments(@Parameter(description = "Идентификатор просьбы", required = true)
                                                       @PathVariable("id") int id) {
        return wishService.getAllWishComments(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Создание нового комментария")
    @PostMapping("{id}/comments")
    public WishCommentInfoDto createWishComment(@Parameter(description = "Идентификатор просьбы", required = true)
                                                @PathVariable("id") int id, @RequestBody WishCommentDto wishCommentDto) {
        return wishService.createWishComment(id, wishCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Обновление информации по комментарию")
    @PutMapping("comments")
    public WishCommentInfoDto updateWishComment(@RequestBody WishCommentDto wishCommentDto, Authentication authentication) {
        return wishService.updateWishComment(wishCommentDto, authentication);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER", "ROLE_VOLUNTEER", "ROLE_VOLUNTEER_COORDINATOR", "ROLE_PATIENT"})
    @Operation(summary = "Удаление комментария к просьбе")
    @DeleteMapping("comments/{id}")
    public void deleteWishComment(@Parameter(description = "Идентификатор комментария", required = true) @PathVariable("id") int id) {
        wishService.deleteWishComment(id);
    }

    @Operation(summary = "Область видимости для просьбы")
    @GetMapping("visibility")
    public List<WishVisibilityDto> getAvailableWishVisibility() {
        return wishService.createWishVisibilityDtoList();
    }

    @Operation(summary = "Отмена просьбы")
    @DeleteMapping("cancel/{id}")
    public WishDto cancelWish(@Parameter(description = "Идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.cancelWish(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER", "ROLE_VOLUNTEER", "ROLE_VOLUNTEER_COORDINATOR", "ROLE_PATIENT"})
    @Operation(summary = "Присоединение к просьбе")
    @PostMapping("/{id}/executors")
    public WishDto joinWish(@Parameter(description = "Идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.joinWish(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Подтверждение исполнения просьбы")
    @PostMapping("/{wishId}/confirmation")
    public WishDto confirmWishExecution(@Parameter(description = "Идентификатор просьбы", required = true)
                                            @PathVariable("wishId") int wishId) {
        return wishService.confirmWishExecution(wishId);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Отклонение исполнения просьбы")
    @PostMapping("{wishId}/decline")
    public WishDto declineWishExecution(@Parameter(description = "Идентификатор просьбы", required = true)
                                            @PathVariable("wishId") int wishId) {
        return wishService.declineWishExecution(wishId);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Исполнение просьбы")
    @PostMapping("/{id}/executed")
    public WishDto executeWish(@Parameter(description = "Идентификатор просьбы", required = true) @PathVariable("id") int id) {
        return wishService.executeWish(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER", "ROLE_VOLUNTEER", "ROLE_VOLUNTEER_COORDINATOR", "ROLE_PATIENT"})
    @Operation(summary = "Удаление текущего исполнителя просьбы")
    @DeleteMapping("{wishId}/executors")
    public WishDto detachFromWish(@Parameter(description = "Идентификатор просьбы", required = true) @PathVariable("wishId") int id) {
        return wishService.detachFromWish(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @Operation(summary = "Удаление исполнителя просьбы администратором")
    @DeleteMapping("{wishId}/executors/{userId}")
    public WishDto detachExecutor(@Parameter(description = "Идентификатор просьбы", required = true)
                                               @PathVariable("wishId") int id,
                                  @Parameter(description = "Идентификатор пользователя", required = true)
                                               @PathVariable("userId") int userId) {
        return wishService.detachExecutor(id, userId);
    }
}
