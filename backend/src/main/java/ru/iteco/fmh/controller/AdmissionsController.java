package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.service.admission.AdmissionService;

@Api(description = "Госпитализация")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admissions")
public class AdmissionsController {

    private final AdmissionService admissionService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @ApiOperation(value = "возвращает полную информацию по госпитализации")
    @GetMapping("/{id}")
    public AdmissionDto getAdmission(
            @ApiParam(value = "идентификатор", required = true) @PathVariable("id") int id
    ) {
        return admissionService.getAdmission(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "создание госпитализации")
    @PostMapping
    public ResponseEntity<AdmissionDto> createAdmission(@RequestBody AdmissionDto admissionDto) {
        AdmissionDto dto = admissionService.createOrUpdateAdmission(admissionDto);
        if (dto == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "обновляет информацию по госпитализации")
    @PutMapping
    public AdmissionDto updateAdmission(@RequestBody AdmissionDto admissionDto) {
        return admissionService.createOrUpdateAdmission(admissionDto);
    }
}
