package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;

import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.List;

@Api(description = "Заявки")
@RestController
@RequestMapping("/claims")
public class ClaimController {
    private ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @ApiOperation(value = "реестр всех заявок")
    @GetMapping
    public List<ClaimDto> getAllClaims() {
        return claimService.getAllClaims();
    }


    @ApiOperation(value = "реестр всех заявок со статусом open and in progress")
    @GetMapping("/open-in-progress")
    public List<ClaimDto> getOpenInProgressClaims() {
        return claimService.getOpenInProgressClaims();
    }

    @ApiOperation(value = "Создание новой заявки")
    @PostMapping
    public Integer createClaim(@RequestBody ClaimDto claimDto) {
        return claimService.createClaim(claimDto);
    }

    @ApiOperation(value = "Получения полной информации заявки по id")
    @GetMapping("/{id}")
    public ClaimDto getClaim(@ApiParam(value = "идентификатор заявки", required = true) @PathVariable int id) {
        return claimService.getClaim(id);
    }

    @ApiOperation(value = "изменение информации по заявке")
    @PutMapping
    public ClaimDto updateClaim(@RequestBody ClaimDto claimDtoDto) {
        return claimService.updateClaim(claimDtoDto);
    }

    @ApiOperation(value = "изменение заявки по статусной модели")
    @PutMapping("{id}/status")
    public ClaimDto changeStatus(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int claimId,
            @ApiParam(value = "новый статус для заявки", required = true) @RequestParam("status") StatusE status) {
        return claimService.changeStatus(claimId, status);
    }

    @ApiOperation(value = "получение полной информации комментария к заявке по id комментария")
    @GetMapping("/comments/{id}")
    public ClaimCommentDto getClaimComment(@PathVariable("id") int claimCommentId) {
        return claimService.getClaimComment(claimCommentId);
    }

    @ApiOperation(value = "получение всех комментариев к заявке")
    @GetMapping("{id}/comments")
    public List<ClaimCommentDto> getAllClaimsComments(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int claimId) {
        return claimService.getAllClaimsComments(claimId);
    }

    @ApiOperation(value = "Создание нового комментария к заявке")
    @PostMapping("{id}/comments")
    public Integer createClaimComment(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int claimId,
            @RequestBody ClaimCommentDto claimCommentDto) {
        return claimService.addComment(claimId, claimCommentDto);
    }

    @ApiOperation(value = "изменение информации по комментарии к заявке")
    @PutMapping("/comments")
    public ClaimCommentDto updateClaimComment(@RequestBody ClaimCommentDto claimCommentDto) {
        return claimService.updateClaimComment(claimCommentDto);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
