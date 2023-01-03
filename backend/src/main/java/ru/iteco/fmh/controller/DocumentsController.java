package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.service.document.DocumentService;

@Tag(name = "Документы")
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private final DocumentService documentService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Загрузка документа")
    @PostMapping("/upload")
    public String uploadDocument(@RequestPart(name = "postcard_image") MultipartFile multipartFile) {
        return documentService.uploadDocument(multipartFile);
    }
}
