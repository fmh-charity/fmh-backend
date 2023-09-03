package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;

import ru.iteco.fmh.dto.employee.*;
import ru.iteco.fmh.service.employee.EmployeeService;
import ru.iteco.fmh.service.user.UserService;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@Tag(name = "Сотрудники")
@RequiredArgsConstructor
@RequestMapping("/employee")
@Validated
public class EmployeesController {

    private final UserService userService;
    private final EmployeeService employeeService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Регистрация сотрудника")
    @PostMapping
    public EmployeeRegistrationResponse createEmployee(@RequestBody @Valid EmployeeRegistrationRequest request) {
        return userService.createEmployee(request);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Получение информации о сотруднике")
    @GetMapping("/{employeeId}")
    public EmployeeInfoDto getEmployeeById(@Parameter(description = "Идентификатор сотрудника", required = true)
                                               @PathVariable int employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }


    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Изменение информации о сотруднике")
    @PutMapping("/{employeeId}")
    public EmployeeInfoDto updateEmployeeById(@Parameter(description = "Идентификатор сотрудника", required = true)
                                                  @PathVariable int employeeId,
                                              @RequestBody @Valid EmployeeChangingRequest employeeChangingRequest) {
        return employeeService.updateEmployeeById(employeeId, employeeChangingRequest);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @GetMapping("")
    public List<EmployeeInfoScheduleDto> getEmployeeList(@RequestParam(required = false) String fullName,
                                                         @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate dateStart,
                                                         @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate dateEnd,
                                                         @RequestParam(defaultValue = "true") boolean isActiveOnly,
                                                         @RequestParam(defaultValue = "true") boolean returnWorkTime) {

        return employeeService.getEmployeeList(fullName, dateStart, dateEnd, isActiveOnly, returnWorkTime);
    }
}



