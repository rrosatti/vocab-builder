package com.example.rodri.vocabbuilder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;
    private MyDataSource dataSource;
    private Util util = new Util();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniViews();

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
    }

    private void login() {
        if (validateFields()) {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            boolean loginSuccess = Login.getInstance().login(username, password, LoginActivity.this);

            if (loginSuccess) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        }
    }

    private boolean validateFields() {
        if (util.isEditTextEmpty(etUsername)) {
            String message = getString(R.string.toast_username_field_empty);
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        } else if (util.isEditTextEmpty(etPassword)) {
            String message = getString(R.string.toast_password_field_empty);
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
