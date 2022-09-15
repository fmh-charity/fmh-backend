package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.pagination.PageablePogo;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.List;


@Api("Заявки")
@RequiredArgsConstructor
@RestController
@RequestMapping("/claims")
public class ClaimsController {

    private final ClaimService claimService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех заявок")
    @GetMapping
    public List<ClaimDto> getAllClaims() {
        return claimService.getAllClaims();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех заявок со статусом open and in progress")
    @GetMapping("/open-in-progress")
    public List<ClaimDto> getOpenInProgressClaims() {
        return claimService.getOpenInProgressClaims();
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание новой заявки")
    @PostMapping
    public ClaimDto createClaim(@RequestBody ClaimDto request) {
        return claimService.createClaim(request);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Получения полной информации заявки по id")
    @GetMapping("/{id}")
    public ClaimDto getClaim(@ApiParam(value = "идентификатор заявки", required = true) @PathVariable int id) {
        return claimService.getClaim(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "изменение информации по заявке")
    @PutMapping
    public ClaimDto updateClaim(@RequestBody ClaimDto claim, Authentication authentication) {
        return claimService.updateClaim(claim, authentication);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "изменение заявки по статусной модели")
    @PutMapping("{id}/status")
    public ClaimDto changeStatus(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int claimId,
            @ApiParam(value = "новый статус для заявки", required = true) @RequestParam("status") Status status,
            @ApiParam(value = "исполнитель") @RequestParam(value = "executorId", required = false) Integer executorId,
            @ApiParam(value = "комментарий", required = true) @RequestBody ClaimCommentDto claimCommentDto) {
        return claimService.changeStatus(claimId, status, executorId, claimCommentDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "получение полной информации комментария к заявке по id комментария")
    @GetMapping("/comments/{id}")
    public ClaimCommentDto getClaimComment(@PathVariable("id") int id) {
        return claimService.getClaimComment(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "получение всех комментариев к заявке")
    @GetMapping("{id}/comments")
    public List<ClaimCommentDto> getAllClaimsComments(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int id) {
        return claimService.getAllClaimsComments(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Создание нового комментария к заявке")
    @PostMapping("{id}/comments")
    public ClaimCommentDto createClaimComment(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int id,
            @RequestBody ClaimCommentDto request) {
        return claimService.addComment(id, request);
    }



    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "изменение информации по комментарии к заявке")
    @PutMapping("/comments")
    public ClaimCommentDto updateClaimComment(@RequestBody ClaimCommentDto request, Authentication authentication) {
        return claimService.updateClaimComment(request, authentication);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "Получение страницы с заявками, с сортировкой")
    @PostMapping("pagination")
    public ResponseEntity<List<ClaimDto>> getPaginationClaims(
        @ApiParam(value = "Объект с номером страницы, количеством элементов на странице и сортировками. Все поля необязательные.\n" +
        "Константы для сортировки: [title, titleReverse, status, statusReverse, createDate, createDateReverse]\n" +
        "Константы для статуса: [IN_PROGRESS, CANCELLED, OPEN, EXECUTED]",
         required = true) @RequestBody PageablePogo pageablePogo) {
        return ResponseEntity.ok(claimService.getPaginationClaims(pageablePogo));
    }
}


