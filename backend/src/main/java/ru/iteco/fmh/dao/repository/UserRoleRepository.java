package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.user.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findUserRoleByUser(User user);
}