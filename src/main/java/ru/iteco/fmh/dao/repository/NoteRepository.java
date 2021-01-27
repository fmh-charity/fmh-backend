package ru.iteco.fmh.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ru.iteco.fmh.model.Note;

public interface NoteRepository extends CrudRepository<Note, Integer> {
}
