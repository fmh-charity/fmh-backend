package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientNoteDto;
import ru.iteco.fmh.service.admission.AdmissionService;
import ru.iteco.fmh.service.note.NoteService;
import ru.iteco.fmh.service.patient.PatientService;

import java.util.List;

/**
 * Информация по пациенту
 */
@Api(description = "Информация по пациенту")
@RestController
//@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final AdmissionService admissionService;
    private final NoteService noteService;


    public PatientController(PatientService patientService, AdmissionService admissionService, NoteService noteService) {
        this.patientService = patientService;
        this.admissionService = admissionService;
        this.noteService = noteService;
    }

    // Что за параметры offset, limit - paging?
    @ApiOperation(value = "реестр всех пациентов с учетом пагинации")
    @GetMapping
    public List<PatientAdmissionDto> getAllPatientsByStatus(
            @ApiParam(value = "начальная позиция пагинации", required = true) @RequestParam("offset") Integer offset,
            @ApiParam(value = "конечная позиция пагинации", required = true) @RequestParam("limit") Integer limit,
            @ApiParam(value = "список статусов для отображения") @RequestParam("patients_status_list") List<String> patientsStatusList
    ) {
        return patientService.getAllPatientsByStatus(patientsStatusList);
    }

    @ApiOperation(value = "создание пациента")
    @PostMapping
    public PatientDto createPatient(@RequestBody PatientDto patientDto) {
        return patientService.createOrUpdatePatient(patientDto);
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
    @GetMapping("/{patientId}/note")
    public List<PatientNoteDto> getNotes(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId
    ) {
        return noteService.getPatientNotes(patientId);
    }

    @ApiOperation(value = "изменение пациента")
    @PatchMapping
    public PatientDto updatePatient(
            @RequestBody PatientDto patientDto
    ) {
       return patientService.createOrUpdatePatient(patientDto);
    }
}
