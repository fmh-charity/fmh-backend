package ru.iteco.fmh.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.RoleE;

@Repository
public interface RoleEntityRepository extends JpaRepository<Role, Integer> {

    Role findByName(RoleE name);
}
