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

}
