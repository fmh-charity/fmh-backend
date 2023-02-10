package ru.iteco.fmh.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
//https://github.com/gingeleski/cucumber-spring-security-tests
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "json:target/cucumber.json"},
    features = "classpath:features")
public class CucumberTests {
}
