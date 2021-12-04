package com.alkemy.ong.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidatorUtil {

    private Pattern pattern;
    private Matcher matcher;
    //taken from https://www.baeldung.com/java-email-validation-regex 4. Strict Regular Expression Validation
    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    //taken from https://es.stackoverflow.com/questions/136325/validar-tel%C3%A9fonos-de-argentina-con-una-expresi%C3%B3n-regular/136406
    private static final String ARG_PHONE_PATTERN = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$";

    public boolean isEmailValid(final String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        if (email == null) {
            return false;
        }
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isPhoneValid(final String phone) {
        pattern = Pattern.compile(ARG_PHONE_PATTERN);
        if (phone == null) {
            return false;
        }
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
