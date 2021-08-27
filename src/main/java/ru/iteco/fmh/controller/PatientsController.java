package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.service.admission.AdmissionService;
import ru.iteco.fmh.service.patient.PatientService;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;


@Api(description = "Информация по пациенту")
@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final WishService wishService;

    @ApiOperation(value = "реестр всех пациентов")
    @GetMapping
    public List<PatientAdmissionDto> getAllPatientsByStatus(
            @ApiParam(value = "список статусов для отображения") @RequestParam("statuses") List<String> statuses
    ) {
        return patientService.getAllPatientsByStatus(statuses);
    }

    @ApiOperation(value = "создание пациента")
    @PostMapping
    public int createPatient(@RequestBody PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id) {
        return patientService.getPatient(id);
    }

    @ApiOperation(value = "возвращает информацию по всем госпитализациям пациента")
    @GetMapping("/{id}/admissions")
    public List<AdmissionDto> getAdmissions(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id
    ) {
        return admissionService.getPatientAdmissions(id);
    }

    @ApiOperation(value = "возвращает информацию по всем просьбам пациента")
    @GetMapping("/{id}/wishes")
    public List<WishDto> getAllWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id
    ) {
        return wishService.getPatientAllWishes(id);
    }

    @ApiOperation(value = "возвращает информацию по всем просьбам пациента со статусом open/in progress")
    @GetMapping("/{id}/wishes/open-in-progress")
    public List<WishDto> getOpenInProgressWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") int id
    ) {
        return wishService.getPatientOpenInProgressWishes(id);
    }

    @ApiOperation(value = "изменение пациента")
    @PutMapping
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return patientService.updatePatient(patientDto);
    }
}
