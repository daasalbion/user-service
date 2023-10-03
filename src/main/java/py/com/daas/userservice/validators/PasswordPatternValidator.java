package py.com.daas.userservice.validators;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import py.com.daas.userservice.annotations.PasswordPattern;

@Getter
public class PasswordPatternValidator extends PatternValidator<PasswordPattern> {

    @Value("${validation.password.pattern.regexp}")
    private String pattern;

}