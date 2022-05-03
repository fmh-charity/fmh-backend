package ru.iteco.fmh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.iteco.fmh.dto.post.PostDto;
import ru.iteco.fmh.service.post.PostService;

import java.util.List;

@Api("Посты")
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Список всех постов")
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAll();
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Создание нового поста")
    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        return postService.createOrUpdatePost(postDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Редактировать пост")
    @PutMapping
    public PostDto updatePost(@RequestBody PostDto postDto) {
        return postService.createOrUpdatePost(postDto);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Просмотр поста")
    @GetMapping("/{id}")
    public PostDto getPost(
            @ApiParam(value = "Идентификатор поста", required = true)
                @PathVariable("id") int id) {
        return postService.getPost(id);
    }

    @Secured("ROLE_ADMINISTRATOR")
    @ApiOperation(value = "Удалить пост")
    @DeleteMapping("/{id}")
    public void deletePost(
            @ApiParam(value = "Идентификатор поста", required = true)
                @PathVariable("id") int id) {
        postService.deletePost(id);
    }

}
