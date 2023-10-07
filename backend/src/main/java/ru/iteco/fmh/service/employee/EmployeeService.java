package ru.iteco.fmh.service.employee;

import ru.iteco.fmh.dto.employee.EmployeeInfoDto;

public interface EmployeeService {

    /**
     * Возвращает информацию по сотруднику, если он есть
     */
    EmployeeInfoDto getEmployeeById(int id);
}
