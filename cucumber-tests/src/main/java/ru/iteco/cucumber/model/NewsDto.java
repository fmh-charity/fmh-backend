package ru.iteco.cucumber.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
public class NewsDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("newsCategoryId")
    private Integer newsCategoryId;
    @JsonProperty("publishDateFrom")
    private LocalDate publishDateFrom;
    @JsonProperty("publishDateTo")
    private LocalDate publishDateTo;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("creatorId")
    private Integer creatorId;
    @JsonProperty("createDate")
    private Long createDate;
    @JsonProperty("publishDate")
    private Long publishDate;
    @JsonProperty("publishEnabled")
    private boolean publishEnabled;
    @JsonProperty("creatorName")
    private String creatorName;
}
