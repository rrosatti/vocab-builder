package com.example.rodri.vocabbuilder.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodri on 2/25/2017.
 */

public class DateUtil {

    public long fromDateToMilliseconds(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(day, month - 1, year);

        return cal.getTimeInMillis();
    }

    public Date fromMillisecondsToDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        return cal.getTime();
    }

    public Calendar fromMillisecondsToCalendar(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        return cal;
    }

    public int[] fromMillisecondsToSeparateDate(long time) {
        int[] dateArray = new int[3];
        Calendar cal = fromMillisecondsToCalendar(time);
        dateArray[0] = cal.get(Calendar.DAY_OF_MONTH);
        dateArray[1] = cal.get(Calendar.MONTH) + 1;
        dateArray[2] = cal.get(Calendar.YEAR);

        return dateArray;
    }

    public String toStringDate(int day, int month, int year) {
        String sDay = String.valueOf(day);
        String sMonth = String.valueOf(month);
        String sYear = String.valueOf(year);
        if (day < 10) {
            sDay = "0" + day;
        }
        if (month < 10) {
            sMonth = "0" + month;
        }

        return sDay + "/" + sMonth + "/" + sYear;
    }


    // plus1Day, plus10Days ...
}
