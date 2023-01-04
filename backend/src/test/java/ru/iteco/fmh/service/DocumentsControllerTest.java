package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.iteco.fmh.service.document.DocumentServiceImpl;

import java.io.File;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class DocumentsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private DocumentServiceImpl documentService;

    @Test
    public void documentUploadTestShouldPassSuccess() throws Exception {
        final String urlSeparator = "/";
        String fileName = documentService.getMd5NameFromDocumentName("testFile.jpg");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("postcard_image", "testFile.jpg", "image/jpg", "someBytes".getBytes());
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/documents/upload")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(content().string(urlSeparator + "documents" + urlSeparator + fileName));
    }
}
