package ru.iteco.fmh.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.PostRepository;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.post.PostDto;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.post.Post;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final ConversionService conversionService;

    @Override
    public List<PostDto> getAll() {
        return postRepo.findAll().stream()
                .map(p -> conversionService.convert(p, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createOrUpdatePost(PostDto postDto) {
        var post = conversionService.convert(postDto, Post.class);
        post = postRepo.save(post);
        return conversionService.convert(post, PostDto.class);
    }

    @Override
    public PostDto getPost(int id) {
        var post = postRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        return conversionService.convert(post, PostDto.class);
    }

    @Override
    public void deletePost(int id) {
        var post = postRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пост с таким ID не существует"));
        post.setDeleted(true);
        postRepo.save(post);
    }
}
