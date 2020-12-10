package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.Advertisement;
import ru.iteco.fmh.model.Employee;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.getAlphabeticString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertisementRepositoryTest {

    @Autowired
    AdvertisementRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testWriteSuccess() {
        Employee employee = getEmployer();
        employee = employeeRepository.save(employee);
        Advertisement entity = Advertisement.builder()
                .author(employee)
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .dateCreate(LocalDate.now())
                .build();

        Advertisement finalEntity = repository.save(entity);

        Employee finalEmployee = employeeRepository.findById(employee.getId()).orElse(null);

        Assertions.assertAll(
                () -> assertNotNull(finalEntity.getId()),
                () -> assertNotNull(finalEmployee)
        );

        repository.delete(entity);
        employeeRepository.delete(employee);
    }

    private Employee getEmployer() {
        return Employee.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .build();
    }
}