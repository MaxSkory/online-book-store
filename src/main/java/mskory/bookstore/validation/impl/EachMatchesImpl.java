package mskory.bookstore.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import mskory.bookstore.validation.EachMatches;

public class EachMatchesImpl implements ConstraintValidator<EachMatches, String[]> {
    private String regex;

    @Override
    public void initialize(EachMatches constraintAnnotation) {
        regex = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Arrays.stream(value).allMatch(s -> s.matches(regex));
    }
}
