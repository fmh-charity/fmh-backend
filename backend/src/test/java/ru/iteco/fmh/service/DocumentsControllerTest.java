package ru.iteco.fmh.service;

import org.hamcrest.core.StringEndsWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class DocumentsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Value("${upload.path}")
    private String uploadPath;

    @Test
    public void documentUploadTestShouldPassSuccess() throws Exception {

        MockMultipartFile mockMultipartFile
                = new MockMultipartFile("postcard_image", "testFile.jpg", "image/jpg", "someBytes".getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/documents/upload")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().string(StringEndsWith.endsWith(".jpg")));
    @Autowired
    DocumentsController sut;
    @Autowired
    DocumentRepository documentRepository;
    @Value("${static-host.host}")
    private String staticHost;
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
