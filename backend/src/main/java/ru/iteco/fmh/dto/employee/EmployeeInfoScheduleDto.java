package ru.iteco.fmh.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
public class EmployeeInfoScheduleDto {

    private final int employeeId;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String position;
    private final List<EmployeeScheduleDto> employeeShedule;
}
