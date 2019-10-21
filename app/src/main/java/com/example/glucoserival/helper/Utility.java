package com.example.glucoserival.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by YASIN on 16,June,2019
 * Email: yasinenubd5@gmail.com
 */
public final class Utility {
    public static final String getFormattedDateForPrentSystem(Calendar dateCalendar) {
        return new SimpleDateFormat("yyyy-MM-dd").format(dateCalendar.getTime());
    }
}
