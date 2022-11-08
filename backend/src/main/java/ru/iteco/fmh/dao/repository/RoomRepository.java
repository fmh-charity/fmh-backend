package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Room;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    /**
     * Найти палату по id
     */
    Room findRoomById(Integer id);

    Optional<Room> findRoomByNameAndDeletedFalse(String name);

    /**
     * Найти все палаты, которые не были удалены
     */
    List<Room> findAllByDeletedIsFalse();

    /**
     * Найти палату по ИД, с проверкой что она не была удалена
     */
    Optional<Room> findByIdAndDeletedIsFalse(int id) throws IllegalArgumentException;

}
