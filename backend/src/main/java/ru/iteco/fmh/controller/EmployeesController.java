package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationResponse;
import ru.iteco.fmh.service.employee.EmployeeService;
import ru.iteco.fmh.service.user.UserService;

import javax.validation.Valid;

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
}
