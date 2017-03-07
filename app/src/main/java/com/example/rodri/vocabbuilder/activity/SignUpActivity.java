package com.example.rodri.vocabbuilder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.util.Util;

/**
 * Created by rodri on 2/1/2017.
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private Button btConfirm;
    private Button btCancel;
    private MyDataSource dataSource;
    private Util util = new Util();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        iniViews();
        dataSource = new MyDataSource(this);

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserAccount();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void iniViews() {
        etName = (EditText) findViewById(R.id.activitySignUp_etName);
        etUsername = (EditText) findViewById(R.id.activitySignUp_etUsername);
        etPassword = (EditText) findViewById(R.id.activitySignUp_etPassword);
        btConfirm = (Button) findViewById(R.id.activitySignUp_btConfirm);
        btCancel = (Button) findViewById(R.id.activitySignUp_btCancel);
    }

    private void createUserAccount() {
        if (validateFields()) {
            String name = etName.getText().toString();
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            try {
                dataSource.open();

                long userId = dataSource.createUser(name, username, password);
                dataSource.createUserNotification(userId, 1);

                if (userId != 0) {
                    String message = getString(R.string.toast_sign_up_success);
                    util.showGreenTheme(SignUpActivity.this, message);
                    finish();
                } else {
                    String message = getString(R.string.toast_sign_up_fail);
                    util.showRedThemeToast(SignUpActivity.this, message);
                    finish();
                }

                dataSource.close();
            } catch (Exception e) {
                e.printStackTrace();
                dataSource.close();
            }

        }

    }

    private boolean validateFields() {
        if (util.isEditTextEmpty(etName)) {
            String message = getString(R.string.toast_name_field_empty);
            util.showRedThemeToast(SignUpActivity.this, message);
            return false;
        } else if (util.isEditTextEmpty(etUsername)) {
            String message = getString(R.string.toast_username_field_empty);
            util.showRedThemeToast(SignUpActivity.this, message);
            return false;
        } else if (util.isEditTextEmpty(etPassword)) {
            String message = getString(R.string.toast_password_field_empty);
            util.showRedThemeToast(SignUpActivity.this, message);
            return false;
        } else {
            return true;
        }
    }
}
