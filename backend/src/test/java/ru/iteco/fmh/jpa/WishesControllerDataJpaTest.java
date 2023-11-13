package ru.iteco.fmh.jpa;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.WishesController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.*;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.security.UserDetailsServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getWishCommentDto;
import static ru.iteco.fmh.TestUtils.getWishCreationInfoDto;
import static ru.iteco.fmh.model.wish.Status.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!

@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
@DataJpaTest
public class WishesControllerDataJpaTest {
    @Autowired
    WishesController sut;

    @Autowired
    WishRepository wishRepository;

    @Autowired
    WishCommentRepository wishCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ConversionService conversionService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private TestEntityManager entityManager;



    @Test
    @AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.ANY)
    public void createOpenWishShouldPassSuccess() {
        // given
       /* WishCreationRequest wishCreationRequest = getWishCreationInfoDto();

        WishDto givenWish = sut.createWish(wishCreationRequest);
        assertEquals(OPEN, givenWish.getStatus());
        Integer wishId = givenWish.getId();
        assertNotNull(givenWish);*/
        List<Wish> allWithNew2=  wishRepository.findAll();
        entityManager.persist(new Wish());
        List<Wish> allWithNew = wishRepository.findAll();

        /*Wish wishResult = wishRepository.findWishById(givenWish.getId());
        assertNotNull(wishResult);
        WishDto expectedWish = conversionService.convert(wishResult, WishDto.class);
        givenWish.setCreator(expectedWish.getCreator());

        assertEquals(givenWish, expectedWish);

        // AFTER - deleting result entity
        wishRepository.deleteById(wishId);*/
    }


}
