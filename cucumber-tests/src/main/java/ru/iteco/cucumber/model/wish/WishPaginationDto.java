package ru.iteco.cucumber.model.wish;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishPaginationDto {
    @JsonProperty("pages")
    int pages;
    @JsonProperty("elements")
    List<WishDto> elements;
}
