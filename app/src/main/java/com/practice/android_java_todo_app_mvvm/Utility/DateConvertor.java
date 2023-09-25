package com.practice.android_java_todo_app_mvvm.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {
    private String datePattern;
    SimpleDateFormat simpleDateFormat;
    public DateConvertor (String datePattern) {
        this.datePattern = datePattern;
        simpleDateFormat = new SimpleDateFormat(this.datePattern);
    }

    public Long dateToLong(String stringDate) {
        try {
            Date date = simpleDateFormat.parse(stringDate);
            return  date.getTime();

        } catch (ParseException parseException) {
            parseException.printStackTrace();
            return null;
        }
    }

    public String LongToDate(Long timestamp) {
        if (timestamp == null) {
            return  null;
        }

        Date date = new Date(timestamp);

        return simpleDateFormat.format(date);
    }
}
