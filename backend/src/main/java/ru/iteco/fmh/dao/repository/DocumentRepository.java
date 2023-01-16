package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.document.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
}
