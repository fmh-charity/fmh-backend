package ru.iteco.fmh.converter.document;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.document.UpdateDocumentRs;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.model.document.Document;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DocumentToUpdateDocumentRsConverter implements Converter<Document, UpdateDocumentRs> {
    private final UserToUserDtoIdFioConverter conversionService;
    @Value("${static-host.host}")
    private String staticHost;

    @SneakyThrows
    @Override
    public UpdateDocumentRs convert(Document source) {
        UserDtoIdFio userDtoIdFio = conversionService.convert(source.getUser());
        return UpdateDocumentRs.builder()
                .id(source.getId())
                .name(source.getName())
                .filePath(new URI(staticHost + source.getFilePath()).normalize().toString())
                .description(source.getDescription())
                .status(source.getStatus())
                .createDate(source.getCreateDate())
                .user(userDtoIdFio)
                .build();

    }
}

