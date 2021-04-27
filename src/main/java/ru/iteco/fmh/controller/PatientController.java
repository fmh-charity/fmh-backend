package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionShortInfoDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.PatientNoteDto;
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

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

//    TODO: Anastasya
//    @RequestMapping(value =  "/patient/{patients_status}",method = RequestMethod.GET)
//    public List<PatientDto>getAllPatientsByStatus(@PathVariable List <PatientsStatus> patients_status) {
//        //получаем список всех пациентов в нужной форме
//        List<PatientAdmissionDto> list = patientService.getAllPatients();
//        return null;
//    }

//    TODO: Anastasya
//    @ApiOperation(value = "реестр всех пациентов с учетом пагинации")
//    @GetMapping
//    public List<PatientDto> getAllPatients(
//            @ApiParam(value = "начальная позиция пагинации", required = true) @RequestParam("offset") Integer offset,
//            @ApiParam(value = "конечная позиция пагинации", required = true) @RequestParam("limit") Integer limit,
//            @ApiParam(value = "показывать только пациентов в хосписе") @RequestParam("patients_status") PatientsStatus patients_status
//    ) {

//        switch (patients_status) {
//            case Active:
//                return patientService.getPatientsByStatus(PatientsStatus.Active);
//                break;
//            case Expected:
//                return patientService.getPatientsByStatus(PatientsStatus.Expected);
//                break;
//            case Discharged:
//                return patientService.getPatientsByStatus(PatientsStatus.Discharged);
//            break;
//            case Non:
//                return patientService.getPatientsByStatus(PatientsStatus.Non);
//            break;
//            default:
//                return patientService.getAllPatients();
//                break;
//        }
//        return null;
//    }

    @ApiOperation(value = "возвращает общую информацию по пациенту")
    @GetMapping("/{id}")
    public PatientDto getPatient(
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer id
    ) {
        return null;
    }

    @ApiOperation(value = "возвращает информацию по госпитализациям пациента")
    @GetMapping("/{patientId}/admission")
    public List<AdmissionShortInfoDto> getAdmissions(
            // TODO: 27.01.2021 Перенести в сервис admissions
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId
    ) {
        return null;
    }

    @ApiOperation(value = "возвращает информацию по запискам пациента")
    @GetMapping("/{patientId}/note")
    public List<PatientNoteDto> getNotes(
            // TODO: 27.01.2021 перенести в сервис Note
            @ApiParam(value = "идентификатор пациента", required = true) @PathVariable Integer patientId
    ) {
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
