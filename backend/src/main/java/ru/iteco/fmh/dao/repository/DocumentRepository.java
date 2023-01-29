package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.util.Collection;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Page<Document> findAllByStatusIn(Collection<DocumentStatus> statuses, Pageable pageable);
}
