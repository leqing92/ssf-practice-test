package sg.edu.nus.iss.practicetest.Model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PresentOrFutureValidator.class)
@Documented
public @interface PresentOrFuture {
    String message() default "{PresentOrFuture.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}