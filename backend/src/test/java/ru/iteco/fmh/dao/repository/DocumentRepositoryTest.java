package ru.iteco.fmh.dao.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class DocumentRepositoryTest {
    @Autowired
    DocumentRepository repository;
    Document entity;

    @Ignore
    @Test
    public void testWriteSuccess() {
        entity = Document.builder()
                .name(TestUtils.getAlphabeticString())
                .filePath(TestUtils.getAlphabeticString())
                .description(TestUtils.getAlphabeticString())
                .createDate(Instant.now())
                .deleted(false)
                .status(DocumentStatus.NEW)
                .build();
        entity = repository.save(entity);
        assertNotNull(entity.getId());
        repository.delete(entity);
    }
}