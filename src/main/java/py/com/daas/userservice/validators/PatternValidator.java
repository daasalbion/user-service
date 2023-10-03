package py.com.daas.userservice.validators;

import static java.util.Objects.nonNull;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public abstract class PatternValidator<T extends Annotation> implements ConstraintValidator<T, String> {
    protected abstract String getPattern();

    @Override
    public boolean isValid(String chars, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(getPattern());
        context.buildConstraintViolationWithTemplate("debe seguir el patrón " + getPattern())
                .addConstraintViolation().disableDefaultConstraintViolation();
        if (nonNull(chars)) {
            return pattern.matcher(chars).matches();
        }

        return true;
    }
}
