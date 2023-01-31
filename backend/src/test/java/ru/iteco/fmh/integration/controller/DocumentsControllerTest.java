package ru.iteco.fmh.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.StringEndsWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.model.document.Document;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.iteco.fmh.TestUtils.getDocumentCreationDtoRq;
import static ru.iteco.fmh.TestUtils.getUser;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class DocumentsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    DocumentRepository documentRepository;

    @Test
    public void createDocumentShouldPassSuccess() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        DocumentCreationDtoRq givenDto = getDocumentCreationDtoRq();
        Document document = Document.builder().name(givenDto.getName()).description(givenDto.getDescription()).user(getUser()).build();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(givenDto);
        Mockito.when(documentRepository.save(Mockito.any())).thenReturn(document);
        mockMvc.perform(post("/documents").contentType(MediaType.APPLICATION_JSON).content(givenDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(document.getName()))
                .andExpect(jsonPath("$.description").value(document.getDescription()));
    }

    @Test
    public void getDocumentByIdShouldPassSuccess() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Document document = Document.builder().id(2).name("Document").user(getUser()).build();
        Mockito.when(documentRepository.findById(Mockito.any())).thenReturn(Optional.of(document));
        mockMvc.perform(MockMvcRequestBuilders.get("/documents/{id}", document.getId()))
                .andExpect(jsonPath("$.id").value(document.getId()))
                .andExpect(jsonPath("$.name").value(document.getName()));
    }

    @Test
    public void documentUploadTestShouldPassSuccess() throws Exception {
        MockMultipartFile mockMultipartFile
                = new MockMultipartFile("postcard_image", "testFile.jpg", "image/jpg", "someBytes".getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/documents/upload")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().string(StringEndsWith.endsWith(".jpg")));
    }
}
