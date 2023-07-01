package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.employee.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeById(int id);
}
