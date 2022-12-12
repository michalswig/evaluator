package com.mike.evaluator;

import java.util.regex.Pattern;

public class LocalDateTimeDateMatcher implements DateMatcher{
    private static Pattern DATE_PATTERN = Pattern.compile(
            "/^(\\d{4,})-(\\d{2})-(\\d{2})' '(\\d{2}):(\\d{2})(?::(\\d{2}(?:\\.\\d+)?))?$/"
    );
    @Override
    public boolean matches(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

}
