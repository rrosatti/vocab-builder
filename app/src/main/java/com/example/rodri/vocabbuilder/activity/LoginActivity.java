package com.example.rodri.vocabbuilder.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.util.Util;

/**
 * Created by rodri on 1/31/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String MY_PREFERENCES = "com.example.rodri.vocabbuilder";
    private static final String AUTO_LOGIN = "com.example.rodri.vocabbuilder.autologin";
    private static final String USER_ID = "com.example.rodri.vocabbuilder.userid";

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;
    private CheckBox checkAutoLogin;
    private MyDataSource dataSource;
    private Util util = new Util();
    private boolean autoLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniViews();
        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        checkAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoLogin = isChecked;
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void iniViews() {
        etUsername = (EditText) findViewById(R.id.activityLogin_etUsername);
        etPassword = (EditText) findViewById(R.id.activityLogin_etPassword);
        btLogin = (Button) findViewById(R.id.activityLogin_btLogin);
        btSignUp = (Button) findViewById(R.id.activityLogin_btSignUp);
        checkAutoLogin = (CheckBox) findViewById(R.id.activityLogin_checkAutoLogin);
    }

    private void login() {
        if (validateFields()) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            boolean loginSuccess = Login.getInstance().login(username, password, LoginActivity.this);

            if (loginSuccess) {

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean(AUTO_LOGIN, autoLogin);
                editor.putLong(USER_ID, Login.getInstance().getUserId());

                editor.apply();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }
    }

    private boolean validateFields() {
        if (util.isEditTextEmpty(etUsername)) {
            String message = getString(R.string.toast_username_field_empty);
            util.showRedThemeToast(this, message);
            return false;
        } else if (util.isEditTextEmpty(etPassword)) {
            String message = getString(R.string.toast_password_field_empty);
            util.showRedThemeToast(this, message);
            return false;
        } else {
            return true;
        }
    }
}
