package ru.iteco.fmh.converter.document;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.document.DocumentInfoDto;
import ru.iteco.fmh.model.document.Document;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class DocumentToDocumentInfoDtoConverter implements Converter<Document, DocumentInfoDto> {

    @Value("${static-host.host}")
    private String staticHost;

    @SneakyThrows
    @Override
    public DocumentInfoDto convert(Document source) {
        return DocumentInfoDto.builder()
                .id(source.getId())
                .description(source.getDescription())
                .filePath(new URI(staticHost + source.getFilePath()).normalize().toString())
                .build();
    }
}
