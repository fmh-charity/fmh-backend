package ru.iteco.fmh.service.employee;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.EmployeeRepository;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.dto.employee.EmployeeInfoScheduleDto;
import ru.iteco.fmh.dto.employee.EmployeeScheduleDto;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.employee.Schedule;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
     private  final EmployeeRepository employeeRepository;
     private final ConversionService conversionService;
    private final ConversionService conversionServiceForScheduledEmployee;

    @Override
    public EmployeeInfoDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee == null) {
            throw new NotFoundException("Cотрудник не найден");
        }
        EmployeeInfoDto employeeInfoDto = conversionService.convert(employee, EmployeeInfoDto.class);
        return employeeInfoDto;
    }

    @Override
    public List<EmployeeInfoScheduleDto> getEmployeeList(String fullName, LocalDate startDate, LocalDate endDate,
                                                         boolean isActiveOnly, boolean returnWorkTime) {

        List<Employee> listEmployee = employeeRepository.findListEmployee(fullName, startDate, endDate, isActiveOnly);
        List<EmployeeInfoScheduleDto> employeeInfoScheduleDtos = (List<EmployeeInfoScheduleDto>) conversionServiceForScheduledEmployee.convert(listEmployee,
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(Employee.class)),
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(EmployeeInfoScheduleDto.class)));
        return employeeInfoScheduleDtos;
    }
}
