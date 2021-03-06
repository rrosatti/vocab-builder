package com.example.rodri.vocabbuilder.activity;

import android.app.Activity;
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
import com.example.rodri.vocabbuilder.adapter.LanguageSpinnerAdapter;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.Login;
import com.example.rodri.vocabbuilder.util.DateUtil;
import com.example.rodri.vocabbuilder.util.Util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
    //private String[] languages;
    private List<String> languages;
    private int langSelected = 0;
    private MyDataSource dataSource;
    private Util util = new Util();
    private DateUtil dateUtil = new DateUtil();
    private LanguageSpinnerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        iniViews();
        dataSource = new MyDataSource(this);
        languages = Arrays.asList(getResources().getStringArray(R.array.languages));

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter = new LanguageSpinnerAdapter(this, languages);
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
                long spacedRepetitionId = dataSource.createSpacedRepetition(dateUtil.getCurrentDate());

                if (wordId != 0 && performanceId != 0 && spacedRepetitionId != 0) {
                    boolean inserted1 = dataSource.createUserWord(userId, wordId);
                    boolean inserted2 = dataSource.createWordLanguage(wordId, langSelected+1);
                    boolean inserted3 = dataSource.createWordPerformance(wordId, performanceId);
                    boolean inserted4 = dataSource.createWordSpacedRepetition(wordId, spacedRepetitionId);

                    if (inserted1 && inserted2 && inserted3 && inserted4) {
                        String message = getString(R.string.toast_new_word_created);
                        util.showGreenTheme(NewWordActivity.this, message);
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else {
                        String message = getString(R.string.toast_something_went_wrong);
                        util.showRedThemeToast(NewWordActivity.this, message);
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
            util.showRedThemeToast(NewWordActivity.this, message);
            return false;
        } else if (util.isEditTextEmpty(etTrans1) && util.isEditTextEmpty(etTrans2) && util.isEditTextEmpty(etTrans3)) {
            String message = getString(R.string.toast_translation_fields_empty);
            util.showRedThemeToast(NewWordActivity.this, message);
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
