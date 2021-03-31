package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.dto.NoteDto;
import ru.iteco.fmh.dto.NoteShortInfoDto;
import ru.iteco.fmh.service.AdmissionService;

import java.util.List;

@Api(description = "Госпитализация")
@RestController
@RequestMapping("/admission")
public class AdmissionController {

    private AdmissionService admissionService;

    @Autowired
    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @ApiOperation(value = "возвращает полную информацию по госпитализации")
    @GetMapping("/{id}")
    public AdmissionDto getNote(
            @ApiParam(value = "идентификатор", required = true)@PathVariable("id") Integer id
    ){
        return admissionService.getAdmissionInfo(id);
    }

    @ApiOperation(value = "создание госпитализации")
    @PostMapping
    public Integer createAdmission(
            @RequestBody AdmissionDto admissionDto
    ){
        return admissionService.createOrUpdateAdmission(admissionDto);
    }

    @ApiOperation(value = "обновляет информацию по госпитализации")
    @PatchMapping
    public void updateAdmission(
            @RequestBody AdmissionDto admissionDto
    ){
        admissionService.createOrUpdateAdmission(admissionDto);
    }
}
