package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.RoleClaimStatus;
import ru.iteco.fmh.model.user.UserRoleClaim;

import java.util.List;

import java.util.Collection;

@Repository
public interface UserRoleClaimRepository extends JpaRepository<UserRoleClaim, Integer> {
    List<UserRoleClaim> findByUserId(Integer userId);

    UserRoleClaim findFirstByUserIdAndStatusIn(Integer id, Collection<RoleClaimStatus> statuses);
}
