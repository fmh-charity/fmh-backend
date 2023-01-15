package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.DocumentStatus;
import ru.iteco.fmh.service.document.DocumentService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Получение списка документов")
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
}
