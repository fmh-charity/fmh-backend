package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.service.admission.AdmissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Госпитализация")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admissions")
public class AdmissionsController {

    private final AdmissionService admissionService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информации по госпитализации")
    @GetMapping("{id}")
    public AdmissionDto getAdmission(@Parameter(description = "Идентификатор", required = true) @PathVariable("id") int id) {
        return admissionService.getAdmission(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание госпитализации")
    @PostMapping
    public AdmissionDto createAdmission(@RequestBody AdmissionDto admissionDto) {
        return admissionService.createOrUpdateAdmission(admissionDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Обновление информации по госпитализации")
    @PutMapping
    public AdmissionDto updateAdmission(@RequestBody AdmissionDto admissionDto) {
        return admissionService.createOrUpdateAdmission(admissionDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление госпитализации")
    @DeleteMapping("{id}")
    public void deleteAdmission(@Parameter(description = "Идентификатор", required = true) @PathVariable("id") Integer id) {
        admissionService.deleteAdmissionById(id);
    }
}