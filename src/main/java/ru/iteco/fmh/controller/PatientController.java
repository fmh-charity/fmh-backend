package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.AdmissionShortInfoDto;
import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.dto.PatientNoteDto;

import java.util.List;

/**
 * Информация по пациенту
 */
@Api(description = "Информация по пациенту")
@RestController
@RequestMapping("/patient")
public class PatientController {

    @ApiOperation(value = "реестр всех пациентов с учетом пагинации")
    @GetMapping
    public List<PatientDto> getAllPatients(
            @ApiParam(value = "начальная позиция пагинации", required = true)@RequestParam("offset") Integer offset,
            @ApiParam(value = "конечная позиция пагинации", required = true)@RequestParam("limit") Integer limit,
            @ApiParam(value = "показывать только пациентов в хосписе")@RequestParam("show_active") Boolean showActive
    ){
        return null;ссссс
    }

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer id
    ){
        return null;
    }

    @ApiOperation(value = "возвращает информацию по госпитализациям пациента")
    @GetMapping("/{patientId}/admission")
    public List<AdmissionShortInfoDto> getAdmissions(
            // TODO: 27.01.2021 Перенести в сервис admissions
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer patientId
    ){
        return null;
    }

    @ApiOperation(value = "возвращает информацию по запискам пациента")
    @GetMapping("/{patientId}/note")
    public List<PatientNoteDto> getNotes(
            // TODO: 27.01.2021 перенести в сервис Note
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer patientId
    ){
        return null;
    }

    @ApiOperation(value = "создание пациента")
    @PostMapping
    public PatientDto createPatient(
            @RequestBody PatientDto patientDto
    ) {
        return null;
    }

    @ApiOperation(value = "изменение пациента")
    @PatchMapping
    public PatientDto updatePatient(
            @RequestBody PatientDto patientDto
    ) {
        return null;
    }
}
