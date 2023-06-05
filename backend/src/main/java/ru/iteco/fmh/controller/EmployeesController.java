package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationResponse;
import ru.iteco.fmh.service.user.UserService;

import javax.validation.Valid;

@RestController
@Tag(name = "Сотрудники")
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeesController {

    private final UserService userService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Регистрация сотрудника")
    @PostMapping
    public EmployeeRegistrationResponse createEmployee(@RequestBody @Valid EmployeeRegistrationRequest request) {
        return userService.createEmployee(request);
    }
}
