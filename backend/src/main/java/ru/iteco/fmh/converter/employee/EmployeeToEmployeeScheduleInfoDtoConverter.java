package ru.iteco.fmh.converter.employee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeInfoScheduleDto;
import ru.iteco.fmh.dto.employee.EmployeeScheduleDto;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.employee.Schedule;

import java.util.List;

/**
 * конвертер из {@link Employee} в {@link EmployeeInfoDto}//для «Управление сотрудниками»
 */
@Component
@RequiredArgsConstructor
public class EmployeeToEmployeeScheduleInfoDtoConverter implements Converter<Employee, EmployeeInfoScheduleDto> {
    private final ConversionService conversionServiceForScheduleList;
    @Override
    public EmployeeInfoScheduleDto convert(@NonNull Employee source) {
        return EmployeeInfoScheduleDto.builder()
                .employeeId(source.getId())
                .firstName(source.getProfile().getFirstName())
                .lastName(source.getProfile().getLastName())
                .middleName(source.getProfile().getMiddleName())
                .position(source.getPosition().getName())
                .employeeShedule((List<EmployeeScheduleDto>) conversionServiceForScheduleList.convert(
                        source.getScheduleList(),
                        TypeDescriptor.collection(List.class,
                                TypeDescriptor.valueOf(Schedule.class))
                        , TypeDescriptor.collection(List.class,
                                TypeDescriptor.valueOf(EmployeeScheduleDto.class)))).build();

    }
}
