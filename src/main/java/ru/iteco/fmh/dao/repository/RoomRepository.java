package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
