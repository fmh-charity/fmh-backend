package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.service.admission.AdmissionService;
import ru.iteco.fmh.service.wish.WishService;
import ru.iteco.fmh.service.patient.PatientService;

import java.util.List;


@Api(description = "Информация по пациенту")
@RestController
@RequestMapping("/patients")
public class PatientsController {
    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final WishService wishService;

    public PatientsController(PatientService patientService, AdmissionService admissionService, WishService wishService) {
        this.patientService = patientService;
        this.admissionService = admissionService;
        this.wishService = wishService;
    }

    @ApiOperation(value = "реестр всех пациентов")
    @GetMapping
    public List<PatientAdmissionDto> getAllPatientsByStatus(
            @ApiParam(value = "список статусов для отображения") @RequestParam("status_list") List<String> statusList
    ) {
        return patientService.getAllPatientsByStatus(statusList);
    }

    @ApiOperation(value = "создание пациента")
    @PostMapping
    public Integer createPatient(@RequestBody PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer patientId) {
        return patientService.getPatient(patientId);
    }

    @ApiOperation(value = "возвращает информацию по всем госпитализациям пациента")
    @GetMapping("/{id}/admissions")
    public List<AdmissionDto> getAdmissions(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer patientId
    ) {
        return admissionService.getPatientAdmissions(patientId);
    }

    @ApiOperation(value = "возвращает информацию по всем просьбам пациента")
    @GetMapping("/{id}/wishes")
    public List<WishDto> getAllWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer patientId
    ) {
        return wishService.getPatientAllWishes(patientId);
    }

    @ApiOperation(value = "возвращает информацию по всем просьбам пациента со статусом open/in progress")
    @GetMapping("/{id}/wishes/open-in-progress")
    public List<WishDto> getOpenInProgressWishes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable("id") Integer patientId
    ) {
        return wishService.getPatientOpenInProgressWishes(patientId);
    }

    @ApiOperation(value = "изменение пациента")
    @PutMapping
    public PatientDto updatePatient(@RequestBody PatientDto patientDto) {
        return patientService.updatePatient(patientDto);
    }

    // все ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
