package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.employee.Employee;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeById(int id);

    @Query(value = "SELECT * FROM employees as e INNER JOIN profile as p on e.profile_id = p.id WHERE CONCAT(p.first_name, ' ', p.last_name) = :fullName and" +
            "e.schedule_start_date between :dateStart and :dateEnd  and e.active in (TRUE, :isActiveOnly)", nativeQuery = true)
    List<Employee> findListEmployee(String fullName, LocalDate dateStart, LocalDate dateEnd, boolean isActiveOnly);


}
