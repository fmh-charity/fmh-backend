package ru.iteco.fmh.converter.employee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeInfoScheduleDto;
import ru.iteco.fmh.dto.employee.EmployeeScheduleDto;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.employee.Schedule;

/**
 * конвертер из {@link Schedule} в {@link EmployeeScheduleDto}//для «Управление сотрудниками»
 */
@Component
@RequiredArgsConstructor
public class ScheduleToScheduleInfoDtoConverter implements Converter<Schedule, EmployeeScheduleDto> {

    @Override
    public EmployeeScheduleDto convert(@NonNull Schedule source) {
        return EmployeeScheduleDto.builder()
                .sheduleId(source.getId())
                .timeEndWork(source.getWorkEndTime())
                .timeStartWork(source.getWorkStartTime())
                .employmentType(source.getEmploymentType())
                .build();

    }
}
