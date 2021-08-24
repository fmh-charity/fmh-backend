package ru.iteco.fmh.service.wish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.wish.WishComment;

import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.task.StatusE.*;

@Service
public class WishServiceImpl implements WishService {
    private final WishRepository wishRepository;
    private final WishCommentRepository wishCommentRepository;
    private final ConversionServiceFactoryBean factoryBean;

    @Autowired
    public WishServiceImpl(WishRepository wishRepository, WishCommentRepository wishCommentRepository, ConversionServiceFactoryBean factoryBean) {
        this.wishRepository = wishRepository;
        this.wishCommentRepository = wishCommentRepository;
        this.factoryBean = factoryBean;
    }

    @Override
    public List<WishDto> getAllWishes() {
        List<Wish> list = wishRepository.findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc();
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, WishDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WishDto> getOpenInProgressWishes() {
        List<Wish> list = wishRepository.findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(List.of(OPEN, IN_PROGRESS));
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, WishDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer createWish(WishDto wishDto) {
        wishDto.setStatus(wishDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Wish wish = factoryBean.getObject().convert(wishDto, Wish.class);
        return wishRepository.save(wish).getId();
    }

    @Override
    public WishDto getWish(Integer wishId) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new IllegalArgumentException("Просьбы с таким ID не существует"));
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(wish, WishDto.class);
    }

    @Transactional
    @Override
    public WishDto updateWish(WishDto wishDto) {
        ConversionService conversionService = factoryBean.getObject();
        wishDto.setStatus(wishDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Wish wish = conversionService.convert(wishDto, Wish.class);
        wish = wishRepository.save(wish);
        return conversionService.convert(wish, WishDto.class);
    }

    @Override
    public List<WishDto> getPatientAllWishes(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return wishRepository.findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(patientId).stream()
                .map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WishDto> getPatientOpenInProgressWishes(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return wishRepository.findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(patientId, List.of(OPEN, IN_PROGRESS)).stream()
                .map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public WishDto changeStatus(Integer wishId, StatusE status) {
        Wish wish = wishRepository.findById(wishId).orElseThrow(() -> new IllegalArgumentException("Просьбы с таким ID не существует"));
        wish.changeStatus(status);
        wish = wishRepository.save(wish);
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(wish, WishDto.class);
    }

    @Override
    public WishCommentDto getWishComment(Integer commentId) {
        WishComment wishComment = wishCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Комментария с таким ID не существует"));
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(wishComment, WishCommentDto.class);
    }

    @Override
    public List<WishCommentDto> getAllWishComments(Integer wishId) {
        List<WishComment> wishCommentList = wishCommentRepository.findAllByWish_Id(wishId);
        ConversionService conversionService = factoryBean.getObject();
        return wishCommentList.stream().map(i->conversionService.convert(i, WishCommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer createWishComment(Integer wishId, WishCommentDto wishCommentDto) {
        ConversionService conversionService = factoryBean.getObject();
        WishComment wishComment = conversionService.convert(wishCommentDto, WishComment.class);
        wishComment.setWish(wishRepository.findById(wishId)
                .orElseThrow(() -> new IllegalArgumentException("Просьбы с таким ID не существует")));
        return wishCommentRepository.save(wishComment).getId();
    }

    @Transactional
    @Override
    public WishCommentDto updateWishComment(WishCommentDto wishCommentDto) {
        ConversionService conversionService = factoryBean.getObject();
        WishComment wishComment = conversionService.convert(wishCommentDto, WishComment.class);
        wishComment = wishCommentRepository.save(wishComment);
        return conversionService.convert(wishComment,WishCommentDto.class);
    }


}
