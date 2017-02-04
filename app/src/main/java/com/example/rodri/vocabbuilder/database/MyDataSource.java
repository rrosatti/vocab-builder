package com.example.rodri.vocabbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Language;
import com.example.rodri.vocabbuilder.model.Performance;
import com.example.rodri.vocabbuilder.model.User;
import com.example.rodri.vocabbuilder.model.Word;
import com.example.rodri.vocabbuilder.util.Util;

import java.util.ArrayList;
import java.util.List;

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
            MySQLiteHelper.COLUMN_TRANSLATION3,
            MySQLiteHelper.COLUMN_ADDED_AT
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

    public long createWord(String name, String trans1, String trans2, String trans3, long addedAt) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION1, trans1);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION2, trans2);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION3, trans3);
        values.put(MySQLiteHelper.COLUMN_ADDED_AT, addedAt);

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

    public User getUser(long userId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER, userColumns,
                MySQLiteHelper.KEY_ID + " = " + userId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        User user = cursorToUser(cursor);
        cursor.close();

        return user;

    }

    public User getUser(String username, String password) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER, userColumns,
                MySQLiteHelper.COLUMN_USERNAME + " = ('" + username + "') AND "
                        + MySQLiteHelper.COLUMN_PASSWORD + " = ('" + password + "')", null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        User user = cursorToUser(cursor);
        cursor.close();

        return user;
    }

    public Word getWord(long wordId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_WORD, wordColumns,
                MySQLiteHelper.KEY_ID + " = " + wordId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Word word = cursorToWord(cursor);
        cursor.close();

        return word;
    }

    public List<Word> getWords(long userId) {
        List<Word> words = new ArrayList<>();
        Cursor cursorWordIds = db.query(MySQLiteHelper.TABLE_USER_WORD, userWordColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, null);

        if (isCursorEmpty(cursorWordIds)) {
            cursorWordIds.close();
            return null;
        }
        cursorWordIds.moveToFirst();

        while(!cursorWordIds.isAfterLast()) {
            Cursor cursorWord = db.query(MySQLiteHelper.TABLE_WORD, wordColumns,
                    MySQLiteHelper.KEY_ID + " = " + cursorWordIds.getLong(1), null, null, null, null, null);

            if (!isCursorEmpty(cursorWord)) {
                cursorWord.moveToFirst();
                words.add(cursorToWord(cursorWord));
            }

        }

        return words;
    }

    public Language getLanguage(long languageId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_LANGUAGE, languageColumns,
                MySQLiteHelper.KEY_ID + " = " + languageId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Language language = cursorToLanguage(cursor);
        cursor.close();

        return language;
    }

    public long getLanguageId(long wordId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_WORD_LANGUAGE, wordLanguageColumns,
                MySQLiteHelper.COLUMN_WORD_ID + " = " + wordId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();

        return cursor.getLong(1);
    }

    public Performance getPerformance(long performanceId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_PERFORMANCE, performanceColumns,
                MySQLiteHelper.KEY_ID + " = " + performanceId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        Performance performance = cursorToPerformance(cursor);
        cursor.close();

        return performance;
    }

    public long getPerformanceId(long wordId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_WORD_PERFORMANCE, wordPerformanceColumns,
                MySQLiteHelper.COLUMN_WORD_ID + " = " + wordId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();

        return cursor.getLong(1);
    }

    public DetailedWord getDetailedWord(long wordId) {
        Word word = getWord(wordId);

        long languageId = getLanguageId(wordId);
        Language language = getLanguage(languageId);

        long performanceId = getPerformanceId(wordId);
        Performance performance = getPerformance(performanceId);

        if (word != null && language != null && performance != null) {
            DetailedWord detailedWord = new DetailedWord(word, language, performance);
            return detailedWord;
        } else {
            System.out.println("Something went wrong. getDetailedWord()");
            return null;
        }
    }

    public List<DetailedWord> getDetailedWords(long userId) {
        List<DetailedWord> detailedWords = new ArrayList<>();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_WORD, userWordColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            detailedWords.add(getDetailedWord(cursor.getLong(1)));
            cursor.moveToNext();
        }
        cursor.close();

        return detailedWords;
    }


    /** --------------- CURSOR TO --------------- **/
    public User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setName(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        return user;
    }

    public Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setId(cursor.getLong(0));
        word.setName(cursor.getString(1));
        word.setTranslation1(cursor.getString(2));
        word.setTranslation2(cursor.getString(3));
        word.setTranslation3(cursor.getString(4));
        word.setAddedAt(cursor.getLong(5));
        return word;
    }

    public Language cursorToLanguage(Cursor cursor) {
        Language language = new Language();
        language.setId(cursor.getLong(0));
        language.setName(cursor.getString(1));
        return language;
    }

    public Performance cursorToPerformance(Cursor cursor) {
        Performance performance = new Performance();
        performance.setId(cursor.getLong(0));
        performance.setCorrect(cursor.getInt(1));
        performance.setIncorrect(cursor.getInt(2));
        return performance;
    }


    /** --------------- OTHER --------------- **/

    private boolean isCursorEmpty(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return false;
        } else {
            System.out.println("The cursor is empty!");
            return true;
        }
    }
}
