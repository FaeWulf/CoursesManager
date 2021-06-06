package com.faewulf.application;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class faewulfUtil {
    public static boolean isValidDate(String str) {
        if(str.length() == 0)
            return false;
        boolean result;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        try {
            simpleDateFormat.parse(str);
            simpleDateFormat.setLenient(false);
            Date.valueOf(str);
            result = true;
        } catch (ParseException | IllegalArgumentException e) {
            result = false;
        }
        return result;
    }

    public static boolean isNumerizable(String str) {
        if(str.length() == 0)
            return false;

        try {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
