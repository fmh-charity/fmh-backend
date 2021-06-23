package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.service.admission.AdmissionService;

@Api(description = "Госпитализация")
@RestController
@RequestMapping("/admission")
public class AdmissionController {

    private AdmissionService admissionService;

    @Autowired
    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @ApiOperation(value = "возвращает полную информацию по госпитализации")
    @GetMapping("/{id}")
    public AdmissionDto getAdmission(
            @ApiParam(value = "идентификатор", required = true) @PathVariable("id") Integer id
    ) {
        return admissionService.getAdmission(id);
    }

    @ApiOperation(value = "создание госпитализации")
    @PostMapping
    public Integer createAdmission(
            @RequestBody AdmissionDto admissionDto
    ) {
        return admissionService.createAdmission(admissionDto);
    }

    @ApiOperation(value = "обновляет информацию по госпитализации")
    @PatchMapping
    public AdmissionDto updateAdmission(
            @RequestBody AdmissionDto admissionDto
    ) {
      return admissionService.updateAdmission(admissionDto);
    }
}
