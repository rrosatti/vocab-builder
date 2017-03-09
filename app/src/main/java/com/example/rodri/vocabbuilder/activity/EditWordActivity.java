package com.example.rodri.vocabbuilder.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rodri.vocabbuilder.R;
import com.example.rodri.vocabbuilder.database.MyDataSource;
import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Word;
import com.example.rodri.vocabbuilder.util.Util;

/**
 * Created by rodri on 3/9/2017.
 */

public class EditWordActivity extends AppCompatActivity {

    private EditText etWord;
    private EditText etTrans1;
    private EditText etTrans2;
    private EditText etTrans3;
    private Button btConfirm;
    private Button btCancel;
    private MyDataSource dataSource;
    private Util util = new Util();
    private DetailedWord detailedWord;
    private String sWord, sTrans1, sTrans2, sTrans3;
    private long wordId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        iniViews();
        dataSource = new MyDataSource(this);
        wordId = getIntent().getExtras().getLong("wordId", 0);
        System.out.println("ID: " + wordId);

        if (wordId != 0) {
            detailedWord = getWord(wordId);

            if (detailedWord != null ){
                updateViews();

                btConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = validateFields();
                        if (checked) {
                            showDialog();
                        }
                    }
                });

                btCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

            } else {
                _finishActivity();
            }

        } else {
            _finishActivity();
        }
    }

    private void iniViews() {
        etWord = (EditText) findViewById(R.id.activityEditWord_etWord);
        etTrans1 = (EditText) findViewById(R.id.activityEditWord_etTrans1);
        etTrans2 = (EditText) findViewById(R.id.activityEditWord_etTrans2);
        etTrans3 = (EditText) findViewById(R.id.activityEditWord_etTrans3);
        btConfirm = (Button) findViewById(R.id.activityEditWord_btConfirm);
        btCancel  = (Button) findViewById(R.id.activityEditWord_btCancel);
    }

    private DetailedWord getWord(long wordId) {
        try {
            dataSource.open();

            return dataSource.getDetailedWord(wordId);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return null;
        }
    }

    private void _finishActivity() {
        String message = getString(R.string.toast_something_went_wrong);
        util.showRedThemeToast(this, message);
    }

    private void updateViews() {
        Word word = detailedWord.getWord();
        etWord.setText(word.getName());
        etTrans1.setText(word.getTranslation1());
        etTrans2.setText(word.getTranslation2());
        etTrans3.setText(word.getTranslation3());
    }

    private boolean validateFields() {
        sWord = etWord.getText().toString();
        sTrans1 = etTrans1.getText().toString();
        sTrans2 = etTrans2.getText().toString();
        sTrans3 = etTrans3.getText().toString();

        if (sWord.isEmpty()) {
            String message = getString(R.string.toast_word_field_empty);
            util.showRedThemeToast(EditWordActivity.this, message);
            return true;
        } else if (sTrans1.isEmpty() && sTrans2.isEmpty() && sTrans3.isEmpty()) {
            String message = getString(R.string.toast_translation_fields_empty);
            util.showRedThemeToast(EditWordActivity.this, message);
            return false;
        }

        return true;
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_save_changes)
                .setCancelable(true);

        builder.setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean saved = saveData();

                if (saved) {
                    String message = getString(R.string.toast_word_updated);
                    util.showGreenTheme(EditWordActivity.this, message);
                    dialog.cancel();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    dialog.cancel();
                }

            }
        });

        builder.show();
    }

    private boolean saveData() {
        try {
            dataSource.open();

            return dataSource.updateWord(wordId, sWord, sTrans1, sTrans2, sTrans3);

        } catch (Exception e) {
            e.printStackTrace();
            dataSource.close();
            return false;
        }
    }
}
