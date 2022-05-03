package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.UsersController;
import ru.iteco.fmh.dto.user.UserShortInfoDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    UsersController sut;

    @Test
    public void getAllUsers() {
        List<UserShortInfoDto> userShortInfoDtoList = sut.getAllUsers();
        assertEquals(5, userShortInfoDtoList.size());
    }
}
