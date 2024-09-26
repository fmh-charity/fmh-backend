package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsPaginationDto {
    @JsonProperty("pages")
    int pages;
    @JsonProperty("elements")
    List<NewsDto> elements;
}
