package com.example.rodri.vocabbuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.model.User;

/**
 * Created by rodri on 3/4/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    private static final String MY_PREFERENCES = "com.example.rodri.vocabbuilder";
    private static final String AUTO_LOGIN = "com.example.rodri.vocabbuilder.autologin";
    private static final String USER_ID = "com.example.rodri.vocabbuilder.userid";

    private SharedPreferences sharedPreferences;
    private MyDataSource dataSource;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        dataSource = new MyDataSource(this);

        if (sharedPreferences.getBoolean(AUTO_LOGIN, false)) {
            user = getUser();

            if (user != null) {
                boolean loginSuccess = Login.getInstance().login(user.getUsername(), user.getPassword(), this);

                if (loginSuccess) {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    showLoginActivity();
                }
            } else {
                showLoginActivity();
            }
        } else {
            showLoginActivity();
        }

    }

    private User getUser() {
        try {
            dataSource.open();

            long userId = sharedPreferences.getLong(USER_ID, 0);
            return dataSource.getUser(userId);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void showLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
