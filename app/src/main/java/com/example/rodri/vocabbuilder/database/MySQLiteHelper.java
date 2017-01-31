package com.example.rodri.vocabbuilder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rodri on 1/31/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "vocab_builder.db";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USER = "user";
    public static final String TABLE_WORD = "word";
    public static final String TABLE_USER_WORD = "user_word";
    public static final String TABLE_LANGUAGE = "language";
    public static final String TABLE_WORD_LANGUAGE = "word_language";
    public static final String TABLE_PERFORMANCE = "performance";
    public static final String TABLE_WORD_PERFORMANCE = "word_performance";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";

    // User: column names { id, name ...}
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    // Word: column names { id, name ... }
    public static final String COLUMN_TRANSLATION1 = "translation1";
    public static final String COLUMN_TRANSLATION2 = "translation2";
    public static final String COLUMN_TRANSLATION3 = "translation3";

    // UserWord: column names
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_WORD_ID = "word_id";

    // Language: column names { id, name }

    // WordLanguage: column names { word_id, ... }
    public static final String COLUMN_LANGUAGE_ID = "language_id";

    // Performance: column names { id, ... }
    public static final String COLUMN_CORRECT = "correct";
    public static final String COLUMN_INCORRECT = "incorrect";

    // WordPerformance: column names { word_id, ... }
    public static final String COLUMN_PERFORMANCE_ID = "performance_id";


    /** -------------- CREATE TABLES -------------- **/

    public static final String CREATE_TABLE_USER =
            "CREATE TABLE " + TABLE_USER + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + COLUMN_USERNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_WORD =
            "CREATE TABLE " + TABLE_WORD + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL, "
            + COLUMN_TRANSLATION1 + " TEXT NOT NULL, "
            + COLUMN_TRANSLATION2 + " TEXT, "
            + COLUMN_TRANSLATION3 + " TEXT);";

    public static final String CREATE_TABLE_USER_WORD =
            "CREATE TABLE " + TABLE_USER_WORD + " ("
            + COLUMN_USER_ID + " INTEGER NOT NULL, "
            + COLUMN_WORD_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + COLUMN_USER_ID + ", " + COLUMN_WORD_ID + "), "
            + "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + " (" + KEY_ID + "), "
            + "FOREIGN KEY (" + COLUMN_WORD_ID + ") REFERENCES " + TABLE_WORD + " (" + KEY_ID + "));";

    public static final String CREATE_TABLE_LANGUAGE =
            "CREATE TABLE " + TABLE_LANGUAGE + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT NOT NULL);";

    public static final String CREATE_TABLE_WORD_LANGUAGE =
            "CREATE TABLE " + TABLE_WORD_LANGUAGE + " ("
            + COLUMN_WORD_ID + " INTEGER NOT NULL, "
            + COLUMN_LANGUAGE_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + COLUMN_WORD_ID + ", " + COLUMN_LANGUAGE_ID + "), "
            + "FOREIGN KEY (" + COLUMN_WORD_ID + ") REFERENCES " + TABLE_WORD + " (" + KEY_ID + "), "
            + "FOREIGN KEY (" + COLUMN_LANGUAGE_ID + ") REFERENCES " + TABLE_LANGUAGE + " (" + KEY_ID + "));";

    public static final String CREATE_TABLE_PERFORMANCE =
            "CREATE TABLE " + TABLE_PERFORMANCE + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CORRECT + " INTEGER NOT NULL, "
            + COLUMN_INCORRECT + " INTEGER NOT NULL);";

    public static final String CREATE_TABLE_WORD_PERFORMANCE =
            "CREATE TABLE " + TABLE_WORD_PERFORMANCE + " ("
            + COLUMN_WORD_ID + " INTEGER NOT NULL, "
            + COLUMN_PERFORMANCE_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + COLUMN_WORD_ID + ", " + COLUMN_PERFORMANCE_ID + "), "
            + "FOREIGN KEY (" + COLUMN_WORD_ID + ") PREFERENCES " + TABLE_WORD + " (" + KEY_ID + "), "
            + "FOREIGN KEY (" + COLUMN_PERFORMANCE_ID + ") REFERENCES " + TABLE_PERFORMANCE + " (" + KEY_ID + "));";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_WORD);
        db.execSQL(CREATE_TABLE_USER_WORD);
        db.execSQL(CREATE_TABLE_LANGUAGE);
        db.execSQL(CREATE_TABLE_WORD_LANGUAGE);
        db.execSQL(CREATE_TABLE_PERFORMANCE);
        db.execSQL(CREATE_TABLE_WORD_PERFORMANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion
        + ". The old data will be deleted.");

        if (newVersion > oldVersion) {
            // Do something
        }
    }
}
