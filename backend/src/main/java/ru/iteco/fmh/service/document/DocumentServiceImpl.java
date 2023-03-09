package ru.iteco.fmh.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.Util;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.document.DocumentByIdRs;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.dto.document.DocumentForAdminPaginationRs;
import ru.iteco.fmh.dto.document.DocumentForAdminRs;
import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.dto.document.DocumentInfoPaginationDto;
import ru.iteco.fmh.exceptions.DuplicateDataException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.dto.document.UpdateDocumentRq;
import ru.iteco.fmh.dto.document.UpdateDocumentRs;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.document.DocumentStatus;
import ru.iteco.fmh.model.user.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    @Value("${upload.path}")
    private String uploadPath;

    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Override
    public DocumentInfoPaginationDto getAllDocumentInfo(int pages, int elements, boolean isAscendingNameSort,
                                                        Collection<DocumentStatus> statuses) {
        Page<Document> documents;

        Pageable pageable = isAscendingNameSort
                ? PageRequest.of(pages, elements, Sort.by("name"))
                : PageRequest.of(pages, elements, Sort.by("name").descending());

        if (statuses == null || statuses.isEmpty()) {
            documents = documentRepository.findAllByStatusIn(List.of(DocumentStatus.PUBLISHED), pageable);
        } else {
            documents = documentRepository.findAllByStatusIn(statuses, pageable);
        }
        return DocumentInfoPaginationDto.builder()
                .pages(documents.getTotalPages())
                .elements(documents.stream()
                        .map(document -> conversionService.convert(document, DocumentInfoDto.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public DocumentForAdminPaginationRs getDocumentsForAdmin(int pages, int elements, boolean isAscendingNameSort) {

        Pageable pageable = isAscendingNameSort
                ? PageRequest.of(pages, elements, Sort.by("name"))
                : PageRequest.of(pages, elements, Sort.by("name").descending());

        Page<Document> documents = documentRepository.findAll(pageable);

        return DocumentForAdminPaginationRs.builder()
                .pages(documents.getTotalPages())
                .elements(documents.stream()
                        .map(document -> conversionService.convert(document, DocumentForAdminRs.class))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public DocumentCreationDtoRs createDocument(DocumentCreationDtoRq documentCreationDtoRq) {
        if (documentRepository.existsByName(documentCreationDtoRq.getName())) {
            throw new DuplicateDataException("Документ с таким именем уже существует!");
        }
        Document document = conversionService.convert(documentCreationDtoRq, Document.class);
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(currentUserLogin);
        document.setCreateDate(Instant.now());
        document.setUser(user);
        document = documentRepository.save(document);
        return conversionService.convert(document, DocumentCreationDtoRs.class);
    }

    @Override
    public String uploadDocument(MultipartFile multipartFile) {
        final String urlSeparator = "/";

        String md5FileName = Util.getMd5NameFromDocumentName(multipartFile.getOriginalFilename());
        File pathToUploadDocument = new File(uploadPath);
        pathToUploadDocument.mkdirs();

        try {
            multipartFile.transferTo(Path.of(uploadPath + md5FileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return urlSeparator + pathToUploadDocument.getName() + urlSeparator + md5FileName;
    }

    @Override
    public DocumentByIdRs getDocument(int id) {
        Document document = documentRepository.findById(id).orElseThrow(() -> new NotFoundException("Документа с таким ID не существует"));
        return conversionService.convert(document, DocumentByIdRs.class);
    }

    @Override
    public void deleteDocument(int id) {
        try {
            documentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Документа с таким ID не существует");
        }
    }

    @Override
    @Transactional
    public UpdateDocumentRs updateDocument(int id, UpdateDocumentRq updateDocumentRq) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Документ с данным ID отсутствует"));
        String documentName = updateDocumentRq.getName();
        if (!document.getName().equals(documentName)
                && documentRepository.existsByName(documentName)) {
            throw new DuplicateDataException("Документ с таким именем уже существует!");
        }
        document.setName(updateDocumentRq.getName());
        document.setDescription(updateDocumentRq.getDescription());
        document.setStatus(updateDocumentRq.getStatus());
        if (updateDocumentRq.getUserId() != document.getUser().getId()) {
            User user = userRepository.findById(updateDocumentRq.getUserId())
                    .orElseThrow(() -> new NotFoundException("Не удалось обновить информацию.Пользователя с таким ID не существует"));
            document.setUser(user);
        }
        documentRepository.save(document);
        return conversionService.convert(document, UpdateDocumentRs.class);
    }
}
