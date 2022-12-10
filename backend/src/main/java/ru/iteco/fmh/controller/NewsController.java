package ru.iteco.fmh.controller;

import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.news.NewsPaginationDto;
import ru.iteco.fmh.service.news.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Tag(name = "Новости")
@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    private final NewsService newsService;

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Реестр всех новостей")
    @GetMapping
    public ResponseEntity<NewsPaginationDto> getNews(
            @Parameter(name = "pages", description = "От 0")
                @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
            @Parameter(name = "elements", description = "От 1 до 200")
                @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,
            @Parameter(name = "publishDate", description = "Сортировка по дате исполнения")
                @RequestParam() boolean publishDate,
            @Parameter(name = "newsCategoryId", description = "Фильтрация по категории")
                @RequestParam(required = false) Integer newsCategoryId,
            @Parameter(name = "publishDateFrom", description = "Выборка новостей от назначенной даты")
                @RequestParam(required = false) LocalDate publishDateFrom,
            @Parameter(name = "publishDateTo", description = "Выборка новостей до назначенной даты")
                @RequestParam(required = false) LocalDate publishDateTo) {
        return ResponseEntity.ok(newsService.getNews(pages, elements, publishDate, newsCategoryId, publishDateFrom, publishDateTo));
    }

    @Secured({"ROLE_ADMINISTRATOR", "ROLE_MEDICAL_WORKER"})
    @Operation(summary = "Получение полной информации по новости")
    @GetMapping("/{id}")
    public NewsDto getNews(@Parameter(name = "Идентификатор новости", required = true) @PathVariable("id") int id) {
        return newsService.getNews(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Создание новости")
    @PostMapping
    public NewsDto createNews(@RequestBody NewsDto dto) {
        return newsService.createNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Обновление информации по новости")
    @PutMapping
    public NewsDto updateNews(@RequestBody NewsDto dto) {
        return newsService.updateNews(dto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @Operation(summary = "Удаление новости")
    @DeleteMapping("/{id}")
    public void deleteNews(@Parameter(name = "Идентификатор новости", required = true) @PathVariable("id") int id) {
        newsService.deleteNews(id);
    }
}