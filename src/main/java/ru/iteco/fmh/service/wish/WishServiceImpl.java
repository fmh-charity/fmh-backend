package ru.iteco.fmh.service.wish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.StatusE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.StatusE.OPEN;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final ConversionServiceFactoryBean factoryBean;

    @Autowired
    public WishServiceImpl(WishRepository wishRepository, ConversionServiceFactoryBean factoryBean) {
        this.wishRepository = wishRepository;
        this.factoryBean = factoryBean;
    }

    @Override
    public List<WishShortInfoDto> getAllNotes() {
        List<Wish> list = wishRepository.findAllByStatusOrderByPlanExecuteDate(OPEN);
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, WishShortInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer createNote(WishDto wishDto) {
        Wish wish = factoryBean.getObject().convert(wishDto, Wish.class);
        return wishRepository.save(wish).getId();
    }


    @Transactional
    @Override
    public WishDto updateNote(WishDto wishDto) {
        ConversionService conversionService = factoryBean.getObject();
        Wish wish = conversionService.convert(wishDto, Wish.class);
        if (OPEN.equals(wish.getStatus())){
            wish = wishRepository.save(wish);
            return  conversionService.convert(wish, WishDto.class);
        }else {
            throw new IllegalArgumentException("невозможно изменить записку с данным статусом");
        }
    }


    @Override
    public WishDto getNote(Integer id) {
        Optional<Wish> optionalNote = wishRepository.findById(id);
        if (optionalNote.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Wish wish = optionalNote.get();
            return conversionService.convert(wish, WishDto.class);
        } else {
            throw new IllegalArgumentException("записка не найдена!");
        }
    }


    @Override
    public List<WishDto> getPatientNotes(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return wishRepository.findAllByPatient_IdAndDeletedIsFalseAndStatus(patientId, OPEN).stream()
                .map(note -> conversionService.convert(note, WishDto.class))
                .collect(Collectors.toList());
    }
//нет больше комента
//    @Transactional
//    @Override
//    public WishDto addComment(Integer noteId, String comment) {
//        Optional<Wish> optionalNote = wishRepository.findById(noteId);
//
//        if (optionalNote.isPresent()) {
//            Wish wish = optionalNote.get();
//            if (!wish.getComment().isEmpty()) {
//                wish.setComment(wish.getComment().concat(", ").concat(comment));
//            } else {
//                wish.setComment(comment);
//            }
//            wish = wishRepository.save(wish);
//            ConversionService conversionService = factoryBean.getObject();
//            return conversionService.convert(wish, WishDto.class);
//        } else {
//            throw new IllegalArgumentException("записка не найдена!");
//        }
//    }

    @Transactional
    @Override
    public WishDto changeStatus(Integer noteId, StatusE status) {
        Optional<Wish> optionalNote = wishRepository.findById(noteId);

        if (optionalNote.isPresent()) {
            Wish wish = optionalNote.get();
            ConversionService conversionService = factoryBean.getObject();
            if (OPEN.equals(wish.getStatus())) {
                status.changeStatus(wish);
                wish = wishRepository.save(wish);
                return conversionService.convert(wish, WishDto.class);
            } else {
                throw new IllegalArgumentException("невозможно изменить статус неактивной записки!");
            }

        } else {
           throw new IllegalArgumentException("записка не найдена!");
        }

    }
}
