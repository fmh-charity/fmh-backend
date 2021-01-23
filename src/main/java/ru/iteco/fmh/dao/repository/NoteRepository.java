package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}
