package com.example.rodri.vocabbuilder.util;

import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodri on 2/2/2017.
 */

public class Util {

    public boolean isEditTextEmpty(EditText editText) {
        return (editText.getText().toString().isEmpty());
    }

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

    public int[] fromMillisecondsToSeparateDate(long time) {
        int[] dateArray = new int[3];
        Date date = fromMillisecondsToDate(time);
        dateArray[0] = date.getDay();
        dateArray[1] = date.getMonth() + 1;
        dateArray[3] = date.getYear();

        return dateArray;
    }
}
