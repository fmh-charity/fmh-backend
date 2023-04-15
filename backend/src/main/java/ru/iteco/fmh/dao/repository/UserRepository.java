package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id);

    User findUserByLogin(String login);

    List<User> findAll();

    @Query(value = "SELECT u from User u inner join u.profile pr where pr.email = :userEmail")
    Optional<User> findUserByProfileEmail(@Param("userEmail") String userEmail);
}
