package ru.iteco.fmh.dao.repository;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DocumentRepositoryTest {
    @Autowired
    DocumentRepository repository;
    Document entity;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_DELETE_DOCUMENT = "delete from document where id = ?";

    //@Test
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
        jdbcTemplate.update(SQL_DELETE_DOCUMENT, entity.getId());


    }
}