package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.service.news.NewsService;

import java.util.List;

@Api(description = "новости")
@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @ApiOperation(value = "реестр всех новостей")
    @GetMapping
    public List<NewsDto> getAllNews() {
        return newsService.getAllNews();
    }

    @ApiOperation(value = "возвращает полную информацию по новости")
    @GetMapping("/{id}")
    public NewsDto getNews(@ApiParam(value = "идентификатор новости", required = true) @PathVariable("id") int id) {
        return newsService.getNews(id);
    }

    @ApiOperation(value = "Создание новой новости")
    @PostMapping
    public int createNews(@RequestBody NewsDto dto) {
        return newsService.createNews(dto);
    }

    @ApiOperation(value = "обновляет информацию по новости")
    @PutMapping
    public NewsDto updateNews(@RequestBody NewsDto dto) {
        return newsService.updateNews(dto);
    }

    @ApiOperation(value = "удаление новости")
    @DeleteMapping("/{id}")
    public int deleteNews(@ApiParam(value = "идентификатор новости", required = true) @PathVariable("id") int id) {
        return newsService.deleteNews(id);
    }
}
