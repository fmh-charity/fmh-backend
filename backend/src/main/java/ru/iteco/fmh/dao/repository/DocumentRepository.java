package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.document.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAllByDeletedIsFalse();
}
