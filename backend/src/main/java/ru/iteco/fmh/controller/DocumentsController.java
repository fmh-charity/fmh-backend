package ru.iteco.fmh.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentForAdminRs;
import ru.iteco.fmh.dto.document.UpdateDocumentRq;
import ru.iteco.fmh.dto.document.UpdateDocumentRs;
import ru.iteco.fmh.service.document.DocumentService;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Документы")
@RequiredArgsConstructor
@RestController
@RequestMapping("/documents")
public class DocumentsController {

    private final DocumentService documentService;

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

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Изменение документа")
    @PutMapping("{id}")
    public UpdateDocumentRs updateDocument(@RequestBody @Valid UpdateDocumentRq updateDocumentRq, @PathVariable("id") int id) {
        return documentService.updateDocument(id, updateDocumentRq);
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информации по документу")
    @GetMapping("{id}")
    public DocumentByIdRs getDocument(@Parameter(description = "Идентификатор документа", required = true) @PathVariable("id") int id) {
        return documentService.getDocument(id);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @Operation(summary = "Получение списка всех файлов загруженных в систему для администратора, кроме удаленных")
    @GetMapping("/admin")
    public List<DocumentForAdminRs> getDocumentsForAdmin() {
        return documentService.getDocumentsForAdmin();
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление документа")
    @DeleteMapping("{id}")
    public void deleteDocument(@Parameter(description = "Идентификатор документа", required = true) @PathVariable("id") int id) {
        documentService.deleteDocument(id);
    }
}
