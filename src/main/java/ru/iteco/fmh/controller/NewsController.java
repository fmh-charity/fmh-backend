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
import ru.iteco.fmh.dto.news.NewsResponseDto;

import java.util.List;

@Api(description = "новости")
@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    @ApiOperation(value = "реестр всех просьб")
    @GetMapping
    public List<NewsResponseDto> getAllNews() {
        return null;
    }

    @ApiOperation(value = "возвращает полную информацию по новости")
    @GetMapping("/{id}")
    public NewsResponseDto getNews(@ApiParam(value = "идентификатор новости", required = true)  @PathVariable("id") int id) {
        return null;
    }

    @ApiOperation(value = "Создание новой новости")
    @PostMapping
    public NewsResponseDto createNews(@RequestBody NewsResponseDto dto) {
        return null;
    }

    @ApiOperation(value = "обновляет информацию по новости")
    @PutMapping
    public NewsResponseDto updateNews(@RequestBody NewsResponseDto dto) {
        return null;
    }

    @ApiOperation(value = "обновляет информацию по новости")
    @DeleteMapping("/{id}")
    public void deleteNews(@ApiParam(value = "идентификатор новости", required = true)  @PathVariable("id") int id) {
    }
}
