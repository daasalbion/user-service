package py.com.daas.userservice.validators;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import py.com.daas.userservice.annotations.EmailPattern;

@Getter
public class EmailPatternValidator extends PatternValidator<EmailPattern> {

    @Value("${validation.email.pattern.regexp}")
    private String pattern;

}