package com.example.rodri.vocabbuilder.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.model.User;

/**
 * Created by rodri on 3/3/2017.
 */

public class AccountActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etUsername;
    private EditText etPassword;
    private Button btChangePassword;
    private Button btConfirm;
    private Button btCancel;
    private LinearLayout containerPassword;
    private MyDataSource dataSource;
    private String sName, sUsername, sPassword;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        iniViews();
        dataSource = new MyDataSource(this);
        user = getUser();

        if (user != null) {
            updateViews();
        } else {
            Toast.makeText(AccountActivity.this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
            finish();
        }

        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerPassword.setVisibility(View.VISIBLE);
                btChangePassword.setVisibility(View.GONE);
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = etName.getText().toString();
                sUsername = etUsername.getText().toString();
                sPassword = etPassword.getText().toString();

                if (sName.isEmpty() || sUsername.isEmpty() || sPassword.isEmpty()) {
                    Toast.makeText(AccountActivity.this, R.string.toast_all_fields_must_be_filled,
                            Toast.LENGTH_SHORT).show();
                } else {
                    showDialog();
                }
            }
        });
    }

    private void iniViews() {
        etName = (EditText) findViewById(R.id.activityAccount_etName);
        etUsername = (EditText) findViewById(R.id.activityAccount_etUsername);
        etPassword = (EditText) findViewById(R.id.activityAccount_etPassword);
        btChangePassword = (Button) findViewById(R.id.activityAccount_btChangePassword);
        btConfirm = (Button) findViewById(R.id.activityAccount_btConfirm);
        btCancel = (Button) findViewById(R.id.activityAccount_btCancel);
        containerPassword = (LinearLayout) findViewById(R.id.activityAccount_containerPassword);
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_save_changes)
                .setCancelable(true);

        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean updated = saveChanges();
                if (updated) {
                    dialog.cancel();
                    finish();
                } else {
                    Toast.makeText(AccountActivity.this, R.string.toast_something_went_wrong, Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });

        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean saveChanges() {
        try {
            dataSource.open();

            long userId = Login.getInstance().getUserId();
            return dataSource.updateUser(userId, sName, sUsername, sPassword);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }
    }

    private User getUser() {
        try {
            dataSource.open();

            return dataSource.getUser(Login.getInstance().getUserId());

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void updateViews() {
        etName.setText(user.getName());
        etUsername.setText(user.getUsername());
        etPassword.setText(user.getPassword());
    }
}
