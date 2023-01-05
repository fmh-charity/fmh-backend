package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.service.document.DocumentService;

import javax.validation.Valid;

@Tag(name = "Документы")
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")

public class DocumentsController {
    private final DocumentService documentService;

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание документа")
    @PostMapping
    public DocumentCreationDtoRs createDocument(@RequestBody @Valid DocumentCreationDtoRq documentCreationDtoRqq) {
        return documentService.createDocument(documentCreationDtoRqq);
    }
}
