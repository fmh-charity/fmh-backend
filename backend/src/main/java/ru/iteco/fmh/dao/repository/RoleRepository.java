package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findAllByIdIn(List<Integer> roleIds);

    Optional<Role> findRoleByName(String roleName);

    List<Role> findAllByNeedConfirmIsTrueAndNameIn(List<String> rolesName);
}
