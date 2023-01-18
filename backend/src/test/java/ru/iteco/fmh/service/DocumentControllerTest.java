package ru.iteco.fmh.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.DocumentsController;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class DocumentControllerTest {
    @Autowired
    DocumentsController sut;
    @Autowired
    DocumentRepository documentRepository;
    @Value("${static-host.host}")
    private String staticHost;

    @Ignore
    @Test
    public void createDocumentShouldPassSuccess() {
        //given

        DocumentCreationDtoRq givenDto = getDocumentCreationDtoRq();

        DocumentCreationDtoRs resultDto = sut.createDocument(givenDto);

        assertAll(
                () -> assertEquals(givenDto.getName(), resultDto.getName()),
                () -> assertEquals(staticHost+givenDto.getFilePath(), resultDto.getFilePath()),
                () -> assertEquals(givenDto.getDescription(), resultDto.getDescription()),
                () -> assertNotNull(resultDto.getId())
        );
        documentRepository.deleteById(resultDto.getId());

    }
}
