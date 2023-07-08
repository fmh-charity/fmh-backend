package ru.iteco.fmh.service.employee;

import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeInfoScheduleDto;
import ru.iteco.fmh.model.employee.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    /**
     *
     *  Возвращает информацию по сотруднику, если он есть
     */
    EmployeeInfoDto getEmployeeById(int id);

    List<EmployeeInfoScheduleDto> getEmployeeList(String fullName, LocalDate startDate, LocalDate endDate, boolean isActiveOnly, boolean returnWorkTime);

}
