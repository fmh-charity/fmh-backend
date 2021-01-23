package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
