package com.example.rodri.vocabbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rodri on 1/31/2017.
 */

public class MyDataSource {

    private SQLiteDatabase db;
    private MySQLiteHelper helper;
    private String[] userColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME,
            MySQLiteHelper.COLUMN_USERNAME,
            MySQLiteHelper.COLUMN_PASSWORD
    };
    private String[] wordColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME,
            MySQLiteHelper.COLUMN_TRANSLATION1,
            MySQLiteHelper.COLUMN_TRANSLATION2,
            MySQLiteHelper.COLUMN_TRANSLATION3
    };
    private String[] userWordColumns = {
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_WORD_ID
    };
    private String[] languageColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.KEY_NAME
    };
    private String[] wordLanguageColumns = {
            MySQLiteHelper.COLUMN_WORD_ID,
            MySQLiteHelper.COLUMN_LANGUAGE_ID
    };
    private String[] performanceColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_CORRECT,
            MySQLiteHelper.COLUMN_INCORRECT
    };
    private String[] wordPerformanceColumns = {
            MySQLiteHelper.COLUMN_WORD_ID,
            MySQLiteHelper.COLUMN_PERFORMANCE_ID
    };

    public MyDataSource(Context context) {
        helper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }


    /** --------------- CREATE --------------- **/

    public long createUser(String name, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);

        // row id, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_USER, null, values);

    }

    public long createWord(String name, String trans1, String trans2, String trans3) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION1, trans1);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION2, trans2);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION3, trans3);

        // row id, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_WORD, null, values);

    }

    public boolean createUserWord(long userId, long wordId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_ID, userId);
        values.put(MySQLiteHelper.COLUMN_WORD_ID, wordId);

        long insertedId = db.insert(MySQLiteHelper.TABLE_USER_WORD, null, values);

        if (insertedId != 0) return true;
        else return false;

    }

    public long createLanguage(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);

        // row id, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_LANGUAGE, null, values);

    }

    public boolean createWordLanguage(long wordId, long languageId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_WORD_ID, wordId);
        values.put(MySQLiteHelper.COLUMN_LANGUAGE_ID, languageId);

        long insertedId = db.insert(MySQLiteHelper.TABLE_WORD_LANGUAGE, null, values);

        if (insertedId != 0) return true;
        else return false;

    }

    public long createPerformance(int correct, int incorrect) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CORRECT, correct);
        values.put(MySQLiteHelper.COLUMN_INCORRECT, incorrect);

        // row id, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_PERFORMANCE, null, values);

    }

    public boolean createWordPerformance(long wordId, long performanceId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_WORD_ID, wordId);
        values.put(MySQLiteHelper.COLUMN_PERFORMANCE_ID, performanceId);

        long insertedId = db.insert(MySQLiteHelper.TABLE_WORD_PERFORMANCE, null, values);

        if (insertedId != 0) return true;
        else return false;

    }


    /** --------------- GET --------------- **/
    /**
     * getUser (userId)
     * getUser (username, password)
     * getWord (wordId)
     * getWordId (userId)
     * getLanguage (languageId)
     * getLanguageId (wordId)
     * getPerformance (performanceId)
     * getPerformanceId (wordId)
     */



    /** --------------- OTHER --------------- **/

    private boolean isCursorEmpty(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }
}
