package ru.iteco.fmh.dto.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@AllArgsConstructor
@Data
@Schema(description = "Информация по расписанию cотрудника")
public class EmployeeInfoScheduleDto {


    private final int employeeId;


    private final String firstName;



    private final String lastName;


    private final String middleName;


    private final String position;


    private final List<EmployeeScheduleDto> employeeShedule;

}
