package ru.iteco.fmh.converter.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeRegistrationResponse;
import ru.iteco.fmh.model.employee.Employee;

/**
 * конвертер из {@link Employee} в {@link EmployeeRegistrationResponse}//для «Управление сотрудниками»
 */
@Component
@RequiredArgsConstructor
public class EmployeeToEmployeeRegistrationResponse implements Converter<Employee, EmployeeRegistrationResponse> {
    @Override
    public EmployeeRegistrationResponse convert(Employee source) {
        return EmployeeRegistrationResponse.builder()
                .employeeId(source.getId())
                .lastName(source.getProfile().getLastName())
                .firstName(source.getProfile().getFirstName())
                .middleName(source.getProfile().getMiddleName())
                .email(source.getProfile().getUser().getLogin())
                .dateOfBirth(source.getProfile().getDateOfBirth())
                .position(source.getPosition().getName())
                .description(source.getDescription())
                .isActive(source.getActive())
                .scheduleType(source.getScheduleType())
                .scheduleStartDate(source.getScheduleStartDate())
                .workStartTime(source.getWorkStartTime())
                .workEndTime(source.getWorkEndTime()).build();
    }
}
