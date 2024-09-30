package ru.iteco.fmh.service.employee;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.EmployeeRepository;
import ru.iteco.fmh.dto.employee.EmployeeInfoDto;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.service.patient.PatientServiceImpl;

import java.util.Optional;

@Service
@AllArgsConstructor
@ConditionalOnClass(PatientServiceImpl.class)
@ConditionalOnProperty
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ConversionService conversionService;


    @Override
    public EmployeeInfoDto getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(e -> conversionService.convert(e, EmployeeInfoDto.class))
                .orElseThrow(() -> new NotFoundException("Cотрудник не найден"));

    }
}
