//package ru.iteco.fmh.dao.repository;
//
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import ru.iteco.fmh.model.news.News;
//import ru.iteco.fmh.model.user.User;
//
//import java.time.LocalDate;
//
//import static org.junit.Assert.assertNotNull;
//import static ru.iteco.fmh.TestUtils.getAlphabeticString;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AdvertisementRepositoryTest {
//
//    @Autowired
//    AdvertisementRepository repository;
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void testWriteSuccess() {
//        User employee = getUser();
//        employee = userRepository.save(employee);
//        News entity = News.builder()
////                .author(employee)
//                .title(getAlphabeticString())
//                .description(getAlphabeticString())
//                .dateCreate(LocalDate.now())
//                .build();
//
//        News finalEntity = repository.save(entity);
//
//        User finalEmployee = userRepository.findById(employee.getId()).orElse(null);
//
//        Assertions.assertAll(
//                () -> assertNotNull(finalEntity.getId()),
//                () -> assertNotNull(finalEmployee)
//        );
//
//        repository.delete(entity);
//        userRepository.delete(employee);
//    }
//
//    private User getUser() {
//        return User.builder()
//                .firstName(getAlphabeticString())
//                .lastName(getAlphabeticString())
//                .middleName(getAlphabeticString())
//                .build();
//    }
//}