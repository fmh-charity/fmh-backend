package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.UserRoleClaim;

@Repository
public interface UserRoleClaimRepository extends JpaRepository<UserRoleClaim, Integer> {
}
