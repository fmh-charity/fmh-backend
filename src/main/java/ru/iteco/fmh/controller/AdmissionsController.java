package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.service.admission.AdmissionService;

@Api(description = "Госпитализация")
@RequiredArgsConstructor
@RestController
@RequestMapping("/admissions")
public class AdmissionsController {

    private final AdmissionService admissionService;

    @ApiOperation(value = "возвращает полную информацию по госпитализации")
    @GetMapping("/{id}")
    public AdmissionDto getAdmission(
            @ApiParam(value = "идентификатор", required = true) @PathVariable("id") int id
    ) {
        return admissionService.getAdmission(id);
    }

    @ApiOperation(value = "создание госпитализации")
    @PostMapping
    public int createAdmission(@RequestBody AdmissionDto admissionDto) {
        return admissionService.createAdmission(admissionDto);
    }

    @ApiOperation(value = "обновляет информацию по госпитализации")
    @PutMapping
    public AdmissionDto updateAdmission(@RequestBody AdmissionDto admissionDto) {
        return admissionService.updateAdmission(admissionDto);
    }
}
