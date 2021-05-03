package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    // найти все записки с активным статусом по id пациента
    List<Note> findAllByPatient_IdAndStatusName_active(Integer patientId);
}
