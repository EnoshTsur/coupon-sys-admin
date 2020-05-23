package com.enosh.couponsystemfinish.validation;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class EntityValidation {

    protected final String EMAIL = "email";
    protected final String NAME = "name";
    protected final String FIRST_NAME = "first name";
    protected final String LAST_NAME = "last name";
    protected final String PASSWORD = "password";

    protected Predicate<String> isValidRange(int min, int max) {
        return str -> str.length() >= min && str.length() <= max;
    }

    protected String existsByError(String name, String attribute) {
        return "Company by the " + attribute + ": " + name + " is already exists";
    }

    protected String lengthError(String attribute, int length) {
        return "Attribute " + attribute + " length should be more than " + length
                + " or equals to";
    }
}
