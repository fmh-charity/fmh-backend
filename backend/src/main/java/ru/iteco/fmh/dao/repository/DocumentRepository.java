package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.document.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

}
