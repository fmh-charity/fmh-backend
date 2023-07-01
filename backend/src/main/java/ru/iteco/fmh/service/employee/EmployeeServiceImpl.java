package ru.iteco.fmh.service.employee;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.EmployeeRepository;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.model.employee.Employee;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
     private  final EmployeeRepository employeeRepository;
     private final ConversionService conversionService;

    @Override
    public EmployeeInfoDto getEmployeeById(int id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        EmployeeInfoDto employeeInfoDto = conversionService.convert(employee, EmployeeInfoDto.class);
        return employeeInfoDto;
    }
}
