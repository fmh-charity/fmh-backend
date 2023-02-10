package ru.iteco.fmh.cucumber.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.Application;

@CucumberContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("cucumber")
public class ParserStepDefs {
    @Дано("^sample feature file is ready$")
    public void givenStatement() {
    }

    @Когда("^I run the feature file$")
    public void whenStatement(){
    }

    @Тогда("^run should be successful$")
    public void thenStatement(){
    }
}
