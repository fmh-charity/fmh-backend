package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findAllByPatient_IdAndDeletedIsFalseAndStatus(Integer patientId, StatusE status);
}
