package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import ru.iteco.fmh.dto.patient.PatientByStatusRs;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.exceptions.IncorrectDataException;
import ru.iteco.fmh.service.patient.PatientService;
import ru.iteco.fmh.service.wish.WishService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Tag(name = "Информация по пациенту")
@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientService patientService;
    private final WishService wishService;

    private final List<String> fields = List.of("id", "room", "status", "profile.firstName", "profile.middleName", "profile.lastName");
    private final List<String> direction = List.of("asc", "desc");

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех пациентов")
    @GetMapping
    public List<PatientByStatusRs> getAllPatientsByStatus(
            @Parameter(name = "pages", description = "От 0")
            @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @Parameter(name = "elements", description = "От 1 до 200")
            @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @Parameter(name = "search", description = "Строка для поиска")
            @RequestParam(required = false) String search,
            @Parameter(name = "sortDirection", description = "Направление сортировки , допустимые значения asc, desc")
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection,
            @Parameter(name = "sortField", description = "Поле по которому будет осуществляться сортирока ,"
                    + "допустимые поля -id,room,status,profile.firstName,profile.middleName,profile.lastName")
            @RequestParam(required = false, defaultValue = "id") String sortField
    ) {
        sortDirection = sortDirection.toLowerCase();
        if (!direction.contains(sortDirection)) {
            throw new IncorrectDataException("Не верное значение в поле направления сортировки");
        }
        if (!fields.contains(sortField)) {
            throw new IncorrectDataException("Не верное значение в поле сортировки");
        }
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        return patientService.getAllPatientsByStatus(PageRequest.of(pages, elements, sort), search);
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