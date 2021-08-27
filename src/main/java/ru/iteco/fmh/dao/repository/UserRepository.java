package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById (Integer id);
}
