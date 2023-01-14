package ru.iteco.fmh.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iteco.fmh.Util;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.exceptions.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
    public void deleteDocument(int id) {
        try {
            documentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Документа с таким ID не существует");
        }
    }
}
