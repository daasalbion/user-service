package py.com.daas.userservice.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import py.com.daas.userservice.validators.PasswordPatternValidator;

@Constraint(validatedBy = PasswordPatternValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.RECORD_COMPONENT})
public @interface PasswordPattern {
    String message() default "El password es invalido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}