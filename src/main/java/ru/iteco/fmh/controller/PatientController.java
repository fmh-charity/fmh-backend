package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.service.admission.AdmissionService;
import ru.iteco.fmh.service.note.NoteService;
import ru.iteco.fmh.service.patient.PatientService;

import java.util.List;


@Api(description = "Информация по пациенту")
@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final NoteService noteService;

    public PatientController(PatientService patientService, AdmissionService admissionService, NoteService noteService) {
        this.patientService = patientService;
        this.admissionService = admissionService;
        this.noteService = noteService;
    }

    @ApiOperation(value = "реестр всех пациентов")
    @GetMapping
    public List<PatientAdmissionDto> getAllPatientsByStatus(
            @ApiParam(value = "список статусов для отображения") @RequestParam("patients_status_list") List<String> patientsStatusList
    ) {
        return patientService.getAllPatientsByStatus(patientsStatusList);
    }

    @ApiOperation(value = "создание пациента")
    @PostMapping
    public Integer createPatient(@RequestBody PatientDto patientDto) {
        return patientService.createPatient(patientDto);
    }

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{patientId}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId) {
        return patientService.getPatient(patientId);
    }

    @ApiOperation(value = "возвращает информацию по всем госпитализациям пациента")
    @GetMapping("/{patientId}/admissions")
    public List<AdmissionDto> getAdmissions(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId
    ) {
        return admissionService.getPatientAdmissions(patientId);
    }

    @ApiOperation(value = "возвращает информацию по запискам пациента")
    @GetMapping("/{patientId}/notes")
    public List<NoteDto> getNotes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId
    ) {
        return noteService.getPatientNotes(patientId);
    }

    @ApiOperation(value = "изменение пациента")
    @PatchMapping
    public PatientDto updatePatient(
            @RequestBody PatientDto patientDto
    ) {
       return patientService.updatePatient(patientDto);
    }
}
