package ru.iteco.fmh.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.DocumentRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRs;
import ru.iteco.fmh.model.document.Document;
import ru.iteco.fmh.model.user.User;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final ConversionService conversionService;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Override
    public DocumentCreationDtoRs createPatient(DocumentCreationDtoRq documentCreationDtoRq) {
        Document document = conversionService.convert(documentCreationDtoRq, Document.class);
        String currentUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByLogin(currentUserLogin);
        document.setUser(user);
        document = documentRepository.save(document);
        return conversionService.convert(document,DocumentCreationDtoRs.class);
    }
}
