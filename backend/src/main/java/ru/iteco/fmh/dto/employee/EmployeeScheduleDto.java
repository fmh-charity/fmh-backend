package ru.iteco.fmh.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class EmployeeScheduleDto {


    private final String date;
    private final String timeStartWork;
    private final String timeEndWork;
    private final String employmentType;
}
