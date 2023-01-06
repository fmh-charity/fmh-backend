package ru.iteco.fmh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.iteco.fmh.controller.DocumentsController;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
@AutoConfigureMockMvc
public class DocumentControllerTest {
    @Autowired
    DocumentsController sut;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createDocumentShouldPassSuccess() {
        //given

        DocumentCreationDtoRq givenDto = getDocumentCreationDtoRq();

        DocumentCreationDtoRs resultDto = sut.createDocument(givenDto);

        assertAll(
                () -> assertEquals(givenDto.getName(), resultDto.getName()),
                () -> assertEquals(givenDto.getFilePath(), resultDto.getFilePath()),
                () -> assertEquals(givenDto.getDescription(), resultDto.getDescription()),
                () -> assertNotNull(resultDto.getId())
        );
        documentRepository.deleteById(resultDto.getId());

    }
}
