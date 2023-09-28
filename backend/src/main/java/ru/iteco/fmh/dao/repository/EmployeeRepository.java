package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.employee.Employee;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employees as e INNER JOIN profile as p on e.profile_id = p.id WHERE (CONCAT(p.first_name, ' ', p.last_name) = :fullName or :fullName is null) and " +
            "e.schedule_start_date between :dateStart and :dateEnd  and e.active in (TRUE, :isActiveOnly)", nativeQuery = true)
    List<Employee> findListEmployee(@Param("fullName") String fullName, @Param("dateStart") LocalDate dateStart,
                                    @Param("dateEnd") LocalDate dateEnd, @Param("isActiveOnly") boolean isActiveOnly);


}
