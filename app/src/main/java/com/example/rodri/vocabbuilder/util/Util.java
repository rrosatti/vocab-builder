package com.example.rodri.vocabbuilder.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rodri on 2/2/2017.
 */

public class Util {

    public boolean isEditTextEmpty(EditText editText) {
        return (editText.getText().toString().isEmpty());
    }

    public void showRedThemeToast(Activity activity, String message) {
        Context context = activity.getApplicationContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        View customView = inflater.inflate(R.layout.toast_red_theme, null);
        Toast toast = new Toast(context);

        TextView txtMessage = (TextView) customView.findViewById(R.id.toastRedTheme_txtMessage);
        txtMessage.setText(message);

        toast.setView(customView);
        showToast(toast);
    }

    public void showGreenTheme(Activity activity, String message) {
        Context context = activity.getApplicationContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        View customView = inflater.inflate(R.layout.toast_green_theme, null);
        Toast toast = new Toast(context);

        TextView txtMessage = (TextView) customView.findViewById(R.id.toastGreenTheme_txtMessage);
        txtMessage.setText(message);

        toast.setView(customView);
        showToast(toast);
    }

    public void showOrangeThemeWithImage(Activity activity, String message, int imageId) {
        Context context = activity.getApplicationContext();
        LayoutInflater inflater = activity.getLayoutInflater();

        View customView = inflater.inflate(R.layout.toast_orange_theme_with_image, null);
        Toast toast = new Toast(context);

        TextView txtMessage = (TextView) customView.findViewById(R.id.toastOrangeThemeWithImage_txtMessage);
        ImageView img = (ImageView) customView.findViewById(R.id.toastOrangeThemeWithImage_img);

        txtMessage.setText(message);
        img.setImageResource(imageId);

        toast.setView(customView);
        showToast(toast);
    }

    public void showToast(Toast toast) {
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

}
