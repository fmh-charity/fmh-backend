package ru.iteco.fmh.converter.employee;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.user.UserEmailDto;
import ru.iteco.fmh.dto.user.UserInfoDto;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * конвертер из {@link Employee} в {@link EmployeeInfoDto}//для «Управление сотрудниками»
 */
@Component
@RequiredArgsConstructor
public class EmployeeToEmployeeInfoDtoConverter implements Converter<Employee, EmployeeInfoDto> {

    @Override
    public EmployeeInfoDto convert(@NonNull Employee source) {
        return EmployeeInfoDto.builder()
                .id(source.getId())
                .firstName(source.getProfile().getFirstName())
                .lastName(source.getProfile().getLastName())
                .middleName(source.getProfile().getMiddleName())
                .workEndTime(source.getWorkEndTime())
                .workStartTime(source.getWorkStartTime())
                .active(source.getActive())
                .scheduleStartDate(source.getScheduleStartDate())
                .scheduleType(source.getScheduleType())
                .build();
    }
}
