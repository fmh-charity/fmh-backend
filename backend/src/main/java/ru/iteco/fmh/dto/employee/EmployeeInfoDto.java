package ru.iteco.fmh.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Data
public class EmployeeInfoDto {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String position;
}
