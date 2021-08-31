package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimCommentRequestDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimRequestDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.List;

@Api(description = "Заявки")
@RequiredArgsConstructor
@RestController
@RequestMapping("/claims")
public class ClaimsController {

    private final ClaimService claimService;

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
    public Integer createClaim(@RequestBody ClaimRequestDto claimDto) {
        return claimService.createClaim(claimDto);
    }

    @ApiOperation(value = "Получения полной информации заявки по id")
    @GetMapping("/{id}")
    public ClaimDto getClaim(@ApiParam(value = "идентификатор заявки", required = true) @PathVariable int id) {
        return claimService.getClaim(id);
    }

    @ApiOperation(value = "изменение информации по заявке")
    @PutMapping
    public ClaimRequestDto updateClaim(@RequestBody ClaimRequestDto dto) {
        return claimService.updateClaim(dto);
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
    public ClaimCommentDto getClaimComment(@PathVariable("id") int id) {
        return claimService.getClaimComment(id);
    }

    @ApiOperation(value = "получение всех комментариев к заявке")
    @GetMapping("{id}/comments")
    public List<ClaimCommentDto> getAllClaimsComments(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int id) {
        return claimService.getAllClaimsComments(id);
    }

    @ApiOperation(value = "Создание нового комментария к заявке")
    @PostMapping("{id}/comments")
    public int createClaimComment(
            @ApiParam(value = "идентификатор заявки", required = true) @PathVariable("id") int id,
            @RequestBody ClaimCommentRequestDto claimCommentDto) {
        return claimService.addComment(id, claimCommentDto);
    }

    @ApiOperation(value = "изменение информации по комментарии к заявке")
    @PutMapping("/comments")
    public  ClaimCommentRequestDto updateClaimComment(@RequestBody  ClaimCommentRequestDto claimCommentDto) {
        return claimService.updateClaimComment(claimCommentDto);
    }
}
