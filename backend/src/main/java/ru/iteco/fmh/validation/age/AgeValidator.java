package ru.iteco.fmh.validation.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    private int minAge;
    private int maxAge;

    @Override
    public void initialize(Age constraintAnnotation) {
        minAge = constraintAnnotation.min();
        maxAge = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.plusYears(minAge).isBefore(LocalDate.now()) && LocalDate.now().minusYears(maxAge).isBefore(localDate);
    }
}
