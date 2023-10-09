package ru.iteco.fmh.service.employee;

import ru.iteco.fmh.dto.employee.*;
import ru.iteco.fmh.dto.user.ProfileChangingRequest;
import ru.iteco.fmh.dto.user.UserShortInfoDto;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {

    /**
     * Возвращает информацию по сотруднику, если он есть
     */
    EmployeeInfoDto getEmployeeById(int id);
    EmployeeInfoDto updateEmployee(int userId, EmployeeChangingRequest profileChangingRequest);


    List<EmployeeInfoScheduleDto> getEmployeeList(String fullName, LocalDate startDate,
                                                  LocalDate endDate, boolean isActiveOnly, boolean returnWorkTime);

}
