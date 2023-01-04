package ru.iteco.fmh.service.document;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploadDocument(MultipartFile multipartFile) {

        String md5FileName = getMd5NameFromDocumentName(multipartFile.getOriginalFilename());
        File pathToUploadDocument = new File(uploadPath);
        pathToUploadDocument.mkdirs();

        try {
            multipartFile.transferTo(Path.of(uploadPath + md5FileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return File.separator + pathToUploadDocument.getName() + File.separator + md5FileName;
    }

    public String getMd5NameFromDocumentName(String documentName) {
        String cleanDocumentName = StringUtils.cleanPath(documentName);
        String documentNameWithoutExtension = FilenameUtils.removeExtension(cleanDocumentName);
        String documentNameExtension = FilenameUtils.getExtension(cleanDocumentName);
        String md5Name = DigestUtils.md5DigestAsHex(documentNameWithoutExtension.getBytes(StandardCharsets.UTF_8));
        return md5Name + "." + documentNameExtension;
    }
}
