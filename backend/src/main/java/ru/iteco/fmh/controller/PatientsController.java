package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.PageDto;
import ru.iteco.fmh.dto.patient.*;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.service.patient.PatientService;
import ru.iteco.fmh.service.wish.WishService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Информация по пациенту")
@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientService patientService;
    private final WishService wishService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(
            summary = "Постраничный поиск пациентов",
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, description = "Номер страницы", name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0", example = "0"))),
                    @Parameter(in = ParameterIn.QUERY, description = "Количество позиций", name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "10", example = "10"))),
                    @Parameter(in = ParameterIn.QUERY, description = "Критерии сортировки в формате (id, asc)", name = "sort",
                            content = @Content(schema = @Schema(type = "string"))),
                    @Parameter(in = ParameterIn.QUERY, description = "Фильтрация по статусу госпитализации", name = "status",
                            content = @Content(schema = @Schema(type = "string", allowableValues = {"DISCHARGED", "ACTIVE", "EXPECTED"}))),
            }
    )
    @GetMapping
    public PageDto<PatientByStatusRs> getAllPatientsByStatus(@PageableDefault(sort = {"id"}) Pageable pageable, @Parameter(hidden = true) SearchPatientsDto searchPatientsDto) {
        return patientService.getPage(pageable, searchPatientsDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание пациента")
    @PostMapping
    public PatientCreateInfoDtoRs createPatient(@RequestBody @Valid PatientCreateInfoDtoRq patientCreateInfoDtoRq) {
        return patientService.createPatient(patientCreateInfoDtoRq);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Возвращает общую информацию по пациенту")
    @GetMapping("{id}")
    public PatientDto getPatient(@Parameter(description = "Идентификатор пациента", required = true)
                                 @PathVariable("id") Integer id) {
        return patientService.getPatient(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "возвращает информацию по всем просьбам пациента")
    @GetMapping("{id}/wishes")
    public List<WishDto> getAllWishes(@Parameter(description = "Идентификатор пациента", required = true)
                                      @PathVariable("id") Integer id) {
        return wishService.getPatientAllWishes(id);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Возвращает информацию по всем просьбам пациента со статусом open/in progress")
    @GetMapping("{id}/wishes/open-in-progress")
    public List<WishDto> getOpenInProgressWishes(@Parameter(description = "Идентификатор пациента", required = true)
                                                 @PathVariable("id") int id) {
        return wishService.getPatientOpenInProgressWishes(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Изменение пациента")
    @PutMapping("{id}")
    public PatientUpdateInfoDtoRs updatePatient(@RequestBody @Valid PatientUpdateInfoDtoRq patientDto, @PathVariable("id") int id) {
        return patientService.updatePatient(id, patientDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление пациента")
    @DeleteMapping("{id}")
    public void deletePatient(@PathVariable("id") int id) {
        patientService.deletePatient(id);
    }
}