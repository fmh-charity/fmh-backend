package ru.iteco.fmh.service.employee;

import ru.iteco.fmh.dto.employee.EmployeeChangingRequest;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;

public interface EmployeeService {

    /**
     * Возвращает информацию по сотруднику, если он есть
     */
    EmployeeInfoDto getEmployeeById(int id);

    EmployeeInfoDto updateEmployeeById(int id, EmployeeChangingRequest employeeChangingRequest);
}
