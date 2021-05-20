package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List <Note> findAllByStatusOrderByPlanExecuteDateAsc (StatusE status);
    List<Note> findAllByPatient_IdAndDeletedIsFalseAndStatus(Integer patientId, StatusE status);
}
