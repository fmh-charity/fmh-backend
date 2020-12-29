package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.dto.PatientNoteDto;
import ru.iteco.fmh.dto.PatientMainInfoDto;

import java.util.List;

/**
 * Информация по пациенту
 */
@Api(description = "Информация по пациенту")
@RestController
@RequestMapping("/patient")
public class PatientController {

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientMainInfoDto getMainInfo(
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer id
    ){
        return null;
    }

    @ApiOperation(value = "возвращает информацию по госпитализациям пациента")
    @GetMapping("/{patientId}/admission")
    public List<AdmissionDto> getAdmissions(
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer patientId
    ){
        return null;
    }

    @ApiOperation(value = "возвращает информацию по запискам пациента")
    @GetMapping("/{patientId}/note")
    public List<PatientNoteDto> getNotes(
            @ApiParam(value = "идентификатор пациента", required = true)@PathVariable Integer patientId
    ){
        return null;
    }

}
