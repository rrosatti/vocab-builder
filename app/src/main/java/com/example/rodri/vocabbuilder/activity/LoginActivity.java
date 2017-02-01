package com.example.rodri.vocabbuilder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rodri.vocabbuilder.R;

/**
 * Created by rodri on 1/31/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniViews();

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
}
