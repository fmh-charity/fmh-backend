package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.DocumentStatus;
import ru.iteco.fmh.service.document.DocumentService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Документы")
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private final DocumentService documentService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение списка документов")
    @GetMapping
    public List<DocumentInfoDto> getDocumentsInfo(@Value("${spring.fmh.documents.static-storage}") String host) {
        List<DocumentStatus> documentStatuses = new ArrayList<>(){};
        documentStatuses.add(DocumentStatus.PUBLISHED);
        return documentService.getDocumentInfo(host, documentStatuses);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Загрузка документа")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadDocument(@RequestPart(name = "postcard_image") MultipartFile multipartFile) {
        return documentService.uploadDocument(multipartFile);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание документа")
    @PostMapping
    public DocumentCreationDtoRs createDocument(@RequestBody @Valid DocumentCreationDtoRq documentCreationDtoRqq) {
        return documentService.createDocument(documentCreationDtoRqq);
    }
}
