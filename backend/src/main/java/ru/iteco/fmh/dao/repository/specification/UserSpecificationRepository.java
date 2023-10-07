package ru.iteco.fmh.dao.repository.specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.user.User;

@Repository
public interface UserSpecificationRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
