package com.riaancornelius.flux.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: riaan.cornelius
 */
public class DateUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy");

    public static String formatDate(Date date){
        return dateFormat.format(date);
    }

    private DateUtil() { }

}
