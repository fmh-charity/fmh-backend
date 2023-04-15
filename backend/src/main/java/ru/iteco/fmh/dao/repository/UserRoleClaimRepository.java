package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.util.List;

@Repository
public interface UserRoleClaimRepository extends JpaRepository<UserRoleClaim, Integer> {
    List<UserRoleClaim> findByUserId(Integer userId);
}
