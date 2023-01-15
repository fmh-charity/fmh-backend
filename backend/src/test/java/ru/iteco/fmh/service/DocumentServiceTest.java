package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.model.document.DocumentStatus;
import ru.iteco.fmh.service.document.DocumentService;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getDocumentCreationDtoRq;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class DocumentServiceTest {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    DocumentService sut;

    @Test
    public void createDocumentShouldPassSuccess() {
        DocumentCreationDtoRq rq = getDocumentCreationDtoRq();
        DocumentCreationDtoRs result = sut.createDocument(rq);
        assertAll(
                () -> assertEquals(1, result.getUser().id()),
                () -> assertEquals(DocumentStatus.NEW, result.getStatus())
        );

        documentRepository.deleteById(result.getId());
    }


}
