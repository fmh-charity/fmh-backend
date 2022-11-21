package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
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
    public PatientCreateInfoDtoRs createPatient(@RequestBody PatientCreateInfoDtoRq patientCreateInfoDtoRq) {
        return patientService.createPatient(patientCreateInfoDtoRq);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id) {
        return patientService.getPatient(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает информацию по всем госпитализациям пациента")
    @GetMapping("/{id}/admissions")
    public List<AdmissionDto> getAdmissions(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer id
    ) {
        return admissionService.getPatientAdmissions(id);
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
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return patientService.updatePatient(patientDto);
    }
}
