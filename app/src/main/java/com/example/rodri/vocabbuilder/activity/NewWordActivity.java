package com.example.rodri.vocabbuilder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.util.Util;

import java.util.Calendar;

/**
 * Created by rodri on 2/5/2017.
 */

public class NewWordActivity extends AppCompatActivity {

    private EditText etWord;
    private EditText etTrans1;
    private EditText etTrans2;
    private EditText etTrans3;
    private Spinner spinnerLangs;
    private Button btConfirm;
    private Button btCancel;
    private String[] languages;
    private int langSelected = 0;
    private MyDataSource dataSource;
    private Util util = new Util();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        iniViews();
        dataSource = new MyDataSource(this);
        languages = getResources().getStringArray(R.array.languages);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        spinnerLangs.setAdapter(adapter);

        spinnerLangs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWord();
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
        etWord = (EditText) findViewById(R.id.activityNewWord_etWord);
        etTrans1 = (EditText) findViewById(R.id.activityNewWord_etTranslation1);
        etTrans2 = (EditText) findViewById(R.id.activityNewWord_etTranslation2);
        etTrans3 = (EditText) findViewById(R.id.activityNewWord_etTranslation3);
        spinnerLangs = (Spinner) findViewById(R.id.activityNewWord_spinnerLanguages);
        btConfirm = (Button) findViewById(R.id.activityNewWord_btConfirm);
        btCancel = (Button) findViewById(R.id.activityNewWord_btCancel);
    }

    private void createWord() {
        if (validateFields()) {
            String word = etWord.getText().toString();
            String trans1 = etTrans1.getText().toString();
            String trans2 = etTrans2.getText().toString();
            String trans3 = etTrans3.getText().toString();

            try {
                dataSource.open();

                long userId = Login.getInstance().getUserId();
                long wordId = dataSource.createWord(word, trans1, trans2, trans3, getDate());
                long performanceId = dataSource.createPerformance(0, 0);

                if (wordId != 0 && performanceId != 0) {
                    boolean inserted1 = dataSource.createUserWord(userId, wordId);
                    boolean inserted2 = dataSource.createWordLanguage(wordId, langSelected+1);
                    boolean inserted3 = dataSource.createWordPerformance(wordId, performanceId);

                    if (inserted1 && inserted2 && inserted3) {
                        String message = getString(R.string.toast_new_word_created);
                        Toast.makeText(NewWordActivity.this, message, Toast.LENGTH_SHORT).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        String message = getString(R.string.toast_something_went_wrong);
                        Toast.makeText(NewWordActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                dataSource.close();
            } catch (Exception e) {
                e.printStackTrace();
                dataSource.close();
            }

        }
    }

    private boolean validateFields() {
        if (util.isEditTextEmpty(etWord)) {
            String message = getString(R.string.toast_word_field_empty);
            Toast.makeText(NewWordActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        } else if (util.isEditTextEmpty(etTrans1) && util.isEditTextEmpty(etTrans2) && util.isEditTextEmpty(etTrans3)) {
            String message = getString(R.string.toast_translation_fields_empty);
            Toast.makeText(NewWordActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private long getDate() {
        Calendar cal = Calendar.getInstance();
        System.out.println("day: " + cal.getTime().getDay());
        return cal.getTimeInMillis();
    }

}
