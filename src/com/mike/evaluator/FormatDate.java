package com.mike.evaluator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatDate {
    SimpleDateFormat format;

    public FormatDate(SimpleDateFormat format) {
        this.format = format;
    }

    public boolean isValidDate(String  input) {
        try {
            format.parse(input);
            return true;
        }
        catch(ParseException e){
            return false;
        }
    }
}
