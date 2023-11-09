package mskory.bookstore.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import mskory.bookstore.validation.impl.EachMatchesImpl;

@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {EachMatchesImpl.class})
public @interface EachMatches {
    String message() default "Price must contains digits!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regex();
}
