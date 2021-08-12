package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.List;

@Api(description = "Заявки")
@RestController
@RequestMapping("/claim")
public class ClaimController {
    private ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @ApiOperation(value = "реестр всех заявок со статусом active")
    @GetMapping
    public List<ClaimShortInfoDto> getAllClaims() {
        return claimService.getAllClaims();
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
    @PatchMapping
    public ClaimDto updateClaim(@RequestBody ClaimDto claimDtoDto) {
        return claimService.updateClaim(claimDtoDto);
    }

    @ApiOperation(value = "изменение заявки по статусной модели")
    @PatchMapping("/status/{claimId}")
    public ClaimDto changeStatus(
            @ApiParam(value = "идентификатор заявки", required = true)@PathVariable("claimId") int claimId,
            @ApiParam(value = "новый статус для заявки", required = true) @RequestParam("status") StatusE status)  {
        return claimService.changeStatus(claimId, status);
    }
}
