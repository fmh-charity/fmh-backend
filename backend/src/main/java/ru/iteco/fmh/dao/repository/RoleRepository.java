package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.RoleName;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByNameIn(List<RoleName> roleNames);

}
