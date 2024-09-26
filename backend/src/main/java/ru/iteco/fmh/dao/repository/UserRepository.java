package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer id);

    User findUserByLogin(String login);

    List<User> findAll();

    @Query(value = "SELECT  w from User w left join w.userRoleClaim wr where wr.status='NEW'or wr.status='REJECTED'")
    List<User> findAllByRoleClaimIsNewOrRejected(PageRequest pageRequest);

    @Query(value = "SELECT  w from User w left join w.userRoleClaim wr where wr.status='CONFIRMED'or wr.status='null'")
    List<User> findAllByRoleClaimIsConfirmedOrNull(PageRequest pageRequest);
}
