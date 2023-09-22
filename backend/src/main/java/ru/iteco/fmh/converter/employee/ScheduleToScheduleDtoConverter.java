package ru.iteco.fmh.converter.employee;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.employee.EmployeeScheduleDto;
import ru.iteco.fmh.model.employee.Schedule;

@Component
public class ScheduleToScheduleDtoConverter implements Converter<Schedule, EmployeeScheduleDto> {
    @Override
    public EmployeeScheduleDto convert(Schedule source) {
        return EmployeeScheduleDto.builder().sheduleId(source.getId())
                .date(source.getDate())
                .timeStartWork(source.getWorkStartTime())
                .timeEndWork(source.getWorkEndTime())
                .employmentType(source.getEmploymentType())
                .build();

    }
}
