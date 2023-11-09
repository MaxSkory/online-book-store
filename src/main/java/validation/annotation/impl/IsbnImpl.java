package validation.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import validation.annotation.Isbn;

public class IsbnImpl implements ConstraintValidator<Isbn, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        int checkDigit = Character.getNumericValue(value.charAt(value.length() - 1));
        String replaced = value.substring(0, value.length() - 1)
                .replaceAll("[-\\s]|(ISBN)", "");
        int[] numbers = replaced.chars()
                .map(Character::getNumericValue)
                .toArray();
        int result = 0;
        if (numbers.length == 9) {
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] *= (i + 1);
            }
            for (int number : numbers) {
                result += number;
            }
            return (result % 11) == checkDigit;
        }
        if (numbers.length == 12) {
            for (int i = 0; i < numbers.length; i++) {
                result += i % 2 == 0 ? numbers[i] : numbers[i] * 3;
            }
            return (10 - (result % 10)) == checkDigit;
        }
        return false;
    }
}
