package ru.iteco.fmh.service.employee;

import ru.iteco.fmh.dto.employee.EmployeeChangingRequest;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeInfoScheduleDto;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationRequest;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    /**
     * Возвращает информацию по сотруднику, если он есть
     */
    EmployeeInfoDto getEmployeeById(int id);

    EmployeeInfoDto updateEmployeeById(int id, EmployeeChangingRequest employeeChangingRequest);

    List<EmployeeInfoScheduleDto> getEmployeeList(String fullName, LocalDate startDate,
                                                  LocalDate endDate, boolean isActiveOnly, boolean returnWorkTime);

}
