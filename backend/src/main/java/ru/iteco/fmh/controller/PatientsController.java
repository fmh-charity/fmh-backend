package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.service.patient.PatientService;
import ru.iteco.fmh.service.wish.WishService;

import java.util.List;


@Api(description = "Информация по пациенту")
@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientService patientService;
    private final WishService wishService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "реестр всех пациентов")
    @GetMapping
    public List<PatientAdmissionDto> getAllPatientsByStatus(
            @ApiParam(value = "список статусов для отображения") @RequestParam("statuses") List<String> statuses
    ) {
        return patientService.getAllPatientsByStatus(statuses);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "создание пациента")
    @PostMapping
    public PatientAdmissionDto createPatient(@RequestBody PatientAdmissionDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id) {
        return patientService.getPatient(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает информацию по всем просьбам пациента")
    @GetMapping("/{id}/wishes")
    public List<WishDto> getAllWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id
    ) {
        return wishService.getPatientAllWishes(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает информацию по всем просьбам пациента со статусом open/in progress")
    @GetMapping("/{id}/wishes/open-in-progress")
    public List<WishDto> getOpenInProgressWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") int id
    ) {
        return wishService.getPatientOpenInProgressWishes(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "изменение пациента")
    @PutMapping
    public PatientAdmissionDto updatePatient(@RequestBody PatientAdmissionDto patientDto) {
        return patientService.updatePatient(patientDto);
    }
}
