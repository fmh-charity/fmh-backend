package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimPaginationDto;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.service.claim.ClaimService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Заявки")
@RequiredArgsConstructor
@RestController
@RequestMapping("/claims")
@Validated
public class ClaimsController {

    private final ClaimService claimService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение страницы с заявками, с сортировкой")
    @GetMapping
    public ResponseEntity<ClaimPaginationDto> getClaims(
            @Parameter(name = "pages", description = "От 0")
            @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @Parameter(name = "elements", description = "От 1 до 200")
            @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @Parameter(name = "status", description = "[IN_PROGRESS, CANCELLED, OPEN, EXECUTED]")
            @RequestParam(name = "status", required = false) List<Status> status,
            @Parameter(name = "createDate", description = "Сортировка по дате исполнения")
            @RequestParam(defaultValue = "true") boolean planExecuteDate) {

        return ResponseEntity.ok(claimService.getClaims(pages, elements, status, planExecuteDate));
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех заявок со статусом open and in progress")
    @GetMapping("open-in-progress")
    public List<ClaimDto> getOpenInProgressClaims() {
        return claimService.getOpenInProgressClaims();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Создание новой заявки")
    @PostMapping
    public ClaimDto createClaim(@RequestBody ClaimDto request) {
        return claimService.createClaim(request);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получения полной информации заявки по id")
    @GetMapping("{id}")
    public ClaimDto getClaim(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable int id) {
        return claimService.getClaim(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Изменение информации по заявке")
    @PutMapping
    public ClaimDto updateClaim(@RequestBody ClaimDto claim, Authentication authentication) {
        return claimService.updateClaim(claim, authentication);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Изменение заявки по статусной модели")
    @PutMapping("{id}/status")
    public ClaimDto changeStatus(
            @Parameter(description = "Идентификатор заявки", required = true) @PathVariable("id") int claimId,
            @Parameter(description = "Новый статус для заявки", required = true) @RequestParam("status") Status status,
            @Parameter(description = "Исполнитель") @RequestParam(value = "executorId", required = false) Integer executorId,
            @Parameter(description = "Комментарий", required = true) @RequestBody ClaimCommentDto claimCommentDto) {
        return claimService.changeStatus(claimId, status, executorId, claimCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информации комментария к заявке по id комментария")
    @GetMapping("comments/{id}")
    public ClaimCommentDto getClaimComment(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable("id") int id) {
        return claimService.getClaimComment(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение всех комментариев к заявке")
    @GetMapping("{id}/comments")
    public List<ClaimCommentDto> getAllClaimsComments(@Parameter(description = "Идентификатор заявки", required = true)
                                                      @PathVariable("id") int id) {
        return claimService.getAllClaimsComments(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Создание нового комментария к заявке")
    @PostMapping("{id}/comments")
    public ClaimCommentDto createClaimComment(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable("id") int id,
                                              @RequestBody ClaimCommentDto request) {
        return claimService.addComment(id, request);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Изменение информации по комментарии к заявке")
    @PutMapping("comments")
    public ClaimCommentDto updateClaimComment(@RequestBody ClaimCommentDto request, Authentication authentication) {
        return claimService.updateClaimComment(request, authentication);
    }
}