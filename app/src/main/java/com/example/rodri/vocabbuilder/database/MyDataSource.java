package com.example.rodri.vocabbuilder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.mtp.MtpConstants;

import com.example.rodri.vocabbuilder.model.DetailedWord;
import com.example.rodri.vocabbuilder.model.Game;
import com.example.rodri.vocabbuilder.model.Language;
import com.example.rodri.vocabbuilder.model.Performance;
import com.example.rodri.vocabbuilder.model.GameLog;
import com.example.rodri.vocabbuilder.model.SpacedRepetition;
import com.example.rodri.vocabbuilder.model.User;
import com.example.rodri.vocabbuilder.model.Word;
import com.example.rodri.vocabbuilder.util.DateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by rodri on 1/31/2017.
 */

public class MyDataSource{

    private SQLiteDatabase db;
    private MySQLiteHelper helper;
    private DateUtil dateUtil = new DateUtil();
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
    private String[] gameLogColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_GAME_ID,
            MySQLiteHelper.COLUMN_WORD_ID,
            MySQLiteHelper.COLUMN_RESULT
    };
    private String[] gameColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_NUM_WORDS,
            MySQLiteHelper.COLUMN_CORRECT,
            MySQLiteHelper.COLUMN_INCORRECT,
            MySQLiteHelper.COLUMN_ADDED_AT
    };
    private String[] userGameColumns = {
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_GAME_ID
    };
    private String[] spacedRepetitionColumns = {
            MySQLiteHelper.KEY_ID,
            MySQLiteHelper.COLUMN_STAGE,
            MySQLiteHelper.COLUMN_CYCLE,
            MySQLiteHelper.COLUMN_LAST_REVIEW,
            MySQLiteHelper.COLUMN_NEXT_REVIEW
    };
    private String[] wordSpacedRepetitionColumns = {
            MySQLiteHelper.COLUMN_WORD_ID,
            MySQLiteHelper.COLUMN_SPACED_REPETITION_ID
    };
    private String[] userNotificationColumns = {
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_NOTIFY
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

    public boolean createGameLog(long gameId, long wordId, int result) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_GAME_ID, gameId);
        values.put(MySQLiteHelper.COLUMN_WORD_ID, wordId);
        values.put(MySQLiteHelper.COLUMN_RESULT, result);

        long insertedId = db.insert(MySQLiteHelper.TABLE_GAME_LOG, null, values);

        if (insertedId != 0) return true;
        else return false;

    }

    public long createGame(int numWords, int correct, int incorrect, long addedAt) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NUM_WORDS, numWords);
        values.put(MySQLiteHelper.COLUMN_CORRECT, correct);
        values.put(MySQLiteHelper.COLUMN_INCORRECT, incorrect);
        values.put(MySQLiteHelper.COLUMN_ADDED_AT, addedAt);

        // row ID, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_GAME, null, values);

    }

    public boolean createUserGame(long userId, long gameId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_ID, userId);
        values.put(MySQLiteHelper.COLUMN_GAME_ID, gameId);

        long insertedId = db.insert(MySQLiteHelper.TABLE_USER_GAME, null, values);

        if (insertedId != 0) return true;
        else return false;
    }

    public long createSpacedRepetition(long lastReview) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_STAGE, 1);
        values.put(MySQLiteHelper.COLUMN_CYCLE, 1);
        values.put(MySQLiteHelper.COLUMN_LAST_REVIEW, lastReview);
        values.put(MySQLiteHelper.COLUMN_NEXT_REVIEW, dateUtil.plusDays(lastReview, 1));

        // Inserted row id, otherwise 0
        return db.insert(MySQLiteHelper.TABLE_SPACED_REPETITION, null, values);

    }

    public boolean createWordSpacedRepetition(long wordId, long spacedRepetitionId) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_WORD_ID, wordId);
        values.put(MySQLiteHelper.COLUMN_SPACED_REPETITION_ID, spacedRepetitionId);

        long insertedId = db.insert(MySQLiteHelper.TABLE_WORD_SPACED_REPETITION, null, values);

        if (insertedId != 0) return true;
        else return false;
    }

    public boolean createUserNotification(long userId, int notify) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_ID, userId);
        values.put(MySQLiteHelper.COLUMN_NOTIFY, notify);

        long insertedId = db.insert(MySQLiteHelper.TABLE_USER_NOTIFICATION, null, values);

        if (insertedId != 0) return true;
        else return false;
    }

    /** --------------- GET --------------- **/
    /**
     * getUser (userId)
     * getUser (username, password)
     * getWord (pos)
     * getWordId (userId)
     * getLanguage (languageId)
     * getLanguageId (pos)
     * getPerformance (performanceId)
     * getPerformanceId (pos)
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

        long spacedRepetitionId = getSpacedRepetitionId(wordId);
        SpacedRepetition sRepetition = getSpacedRepetition(spacedRepetitionId);

        if (word != null && language != null && performance != null) {
            DetailedWord detailedWord = new DetailedWord(word, language, performance, sRepetition);
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

    public List<DetailedWord> getDetailedWords(long userId, int limit) {
        List<DetailedWord> detailedWords = new ArrayList<>();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_WORD, userWordColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, String.valueOf(limit));

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

    public List<DetailedWord> getDetailedWords(List<Long> wordsIds) {
        List<DetailedWord> detailedWords = new ArrayList<>();
        for (int i=0; i<wordsIds.size(); i++) {
            DetailedWord dWord = getDetailedWord(wordsIds.get(i));
            if (dWord != null) {
                detailedWords.add(dWord);
            }
        }

        return detailedWords;
    }

    public List<Long> getDetailedWordsIds(long userId, int limit) {
        List<Long> wordsIds = new ArrayList<>();

        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_WORD, userWordColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, String.valueOf(limit));

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long wordId = getDetailedWord(cursor.getLong(1)).getWord().getId();
            wordsIds.add(wordId);
            cursor.moveToNext();
        }
        cursor.close();

        return wordsIds;
    }

    public LinkedList<Long> getWordsThatNeedToReview(long userId, int limit) {
        LinkedList<Long> wordsIds = new LinkedList<>();
        long currentDate = dateUtil.getCurrentDate();
        /**
         * SELECT * FROM user_word uw
                INNER JOIN word_spaced_repetition  wsr ON wsr.word_id = uw.word_id
                    WHERE uw.user_id = x
                    WHERE wsr.next_review <= current_date
                    LIMIT y
         */
        String sLimit = "";
        if (limit > 0) {
            sLimit = " LIMIT " + limit;
        }

        String sRawQuery = "SELECT * FROM " + MySQLiteHelper.TABLE_USER_WORD + " uw "
                + "INNER JOIN " + MySQLiteHelper.TABLE_WORD_SPACED_REPETITION + " wsr ON "
                + " wsr." + MySQLiteHelper.COLUMN_WORD_ID + " = uw." + MySQLiteHelper.COLUMN_WORD_ID
                + " INNER JOIN " + MySQLiteHelper.TABLE_SPACED_REPETITION + " sr ON "
                + " sr." + MySQLiteHelper.KEY_ID + " = wsr." + MySQLiteHelper.COLUMN_SPACED_REPETITION_ID
                + " WHERE uw." + MySQLiteHelper.COLUMN_USER_ID + " = " + userId
                + " AND sr." + MySQLiteHelper.COLUMN_NEXT_REVIEW + " <= " + currentDate
                + " ORDER BY sr." + MySQLiteHelper.COLUMN_NEXT_REVIEW;

        Cursor cursor = db.rawQuery(sRawQuery + sLimit, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordsIds.add(cursor.getLong(1));
            cursor.moveToNext();
        }

        cursor.close();
        return wordsIds;
    }

    public LinkedList<DetailedWord> getDetailedWordsInReviewOrder(long userId) {
        LinkedList<DetailedWord> detailedWords = new LinkedList<>();

        String sRawQuery = "SELECT * FROM " + MySQLiteHelper.TABLE_USER_WORD + " uw "
                + "INNER JOIN " + MySQLiteHelper.TABLE_WORD_SPACED_REPETITION + " wsr ON "
                + " wsr." + MySQLiteHelper.COLUMN_WORD_ID + " = uw." + MySQLiteHelper.COLUMN_WORD_ID
                + " INNER JOIN " + MySQLiteHelper.TABLE_SPACED_REPETITION + " sr ON "
                + " sr." + MySQLiteHelper.KEY_ID + " = wsr." + MySQLiteHelper.COLUMN_SPACED_REPETITION_ID
                + " WHERE uw." + MySQLiteHelper.COLUMN_USER_ID + " = " + userId
                + " ORDER BY sr." + MySQLiteHelper.COLUMN_NEXT_REVIEW;

        Cursor cursor = db.rawQuery(sRawQuery, null);

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

    public List<GameLog> getMostRecentlyGames(long wordId) {
        List<GameLog> log = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_GAME_LOG, gameLogColumns,
                MySQLiteHelper.COLUMN_WORD_ID + " = " + wordId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            log.add(cursorToGameLog(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return log;
    }

    /**
     * Get the most recently played games for the given word
     * @param wordId
     * @param limit
     * @return
     */
    public List<GameLog> getMostRecentlyGames(long wordId, int limit) {
        List<GameLog> log = new ArrayList<>();

        /**
         * SELECT * FROM game_log gl
                INNER JOIN game g ON g.id = gl.game_id
                    WHERE gl.word_id = x
                    ORDER BY g.added_at DESC
                    LIMIT y
         */
        Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_GAME_LOG +
                " gl INNER JOIN " + MySQLiteHelper.TABLE_GAME + " g ON " +
                "g." + MySQLiteHelper.KEY_ID + " = gl." + MySQLiteHelper.COLUMN_GAME_ID +
                " WHERE gl." + MySQLiteHelper.COLUMN_WORD_ID + " = " + wordId +
                " ORDER BY g." + MySQLiteHelper.COLUMN_ADDED_AT + " DESC " +
                "LIMIT " + limit, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            log.add(cursorToGameLog(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return log;
    }

    public List<GameLog> getGameLogByGameId(long gameId) {
        List<GameLog> log = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_GAME_LOG, gameLogColumns,
                MySQLiteHelper.COLUMN_GAME_ID + " = " + gameId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            log.add(cursorToGameLog(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return log;
    }

    public Game getGame(long gameId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_GAME, gameColumns,
                MySQLiteHelper.KEY_ID + " = " + gameId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        Game game = cursorToGame(cursor);
        cursor.close();

        return game;
    }

    public List<Game> getGames(long userId) {
        List<Game> games = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_GAME, userGameColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            games.add(getGame(cursor.getLong(1)));
            cursor.moveToNext();
        }

        cursor.close();
        return games;
    }

    public SpacedRepetition getSpacedRepetition(long spacedRepetitionId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_SPACED_REPETITION, spacedRepetitionColumns,
                MySQLiteHelper.KEY_ID + " = " + spacedRepetitionId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        SpacedRepetition spacedRepetition = cursorToSpacedRepetition(cursor);
        cursor.close();
        return spacedRepetition;

    }

    public long getSpacedRepetitionId(long wordId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_WORD_SPACED_REPETITION, wordSpacedRepetitionColumns,
                MySQLiteHelper.COLUMN_WORD_ID + " = " + wordId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return 0;
        }
        cursor.moveToFirst();

        return cursor.getLong(1);
    }

    public List<Long> getAllWordsThatNeedToReview() {
        List<Long> wordsIds = new ArrayList<>();

        long currentDate = dateUtil.getCurrentDate();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USER_WORD + " uw "
                + "INNER JOIN " + MySQLiteHelper.TABLE_WORD_SPACED_REPETITION + " wsr ON "
                + " wsr." + MySQLiteHelper.COLUMN_WORD_ID + " = uw." + MySQLiteHelper.COLUMN_WORD_ID
                + " INNER JOIN " + MySQLiteHelper.TABLE_SPACED_REPETITION + " sr ON "
                + " sr." + MySQLiteHelper.KEY_ID + " = wsr." + MySQLiteHelper.COLUMN_SPACED_REPETITION_ID
                + " WHERE sr." + MySQLiteHelper.COLUMN_NEXT_REVIEW + " <= " + currentDate
                + " ORDER BY sr." + MySQLiteHelper.COLUMN_STAGE + " DESC ", null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordsIds.add(cursor.getLong(1));
            cursor.moveToNext();
        }

        cursor.close();
        return wordsIds;
    }

    public List<Long> getUsersActiveNotification() {
        List<Long> userIds = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_NOTIFICATION, userNotificationColumns,
                MySQLiteHelper.COLUMN_NOTIFY + " = " + 1, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            userIds.add(cursor.getLong(0));
            cursor.moveToNext();
        }

        cursor.close();
        return userIds;
    }

    /** --------------- CURSOR TO --------------- **/

    public User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setUsername(cursor.getString(2));
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

    public GameLog cursorToGameLog(Cursor cursor) {
        GameLog gameLog = new GameLog();
        gameLog.setId(cursor.getLong(0));
        gameLog.setGameId(cursor.getLong(1));
        gameLog.setWordId(cursor.getLong(2));
        gameLog.setResult(cursor.getInt(3));
        return gameLog;
    }

    public Game cursorToGame(Cursor cursor) {
        Game game = new Game();
        game.setId(cursor.getLong(0));
        game.setNumWords(cursor.getInt(1));
        game.setCorrect(cursor.getInt(2));
        game.setIncorrect(cursor.getInt(3));
        game.setAddedAt(cursor.getLong(4));
        return game;
    }

    public SpacedRepetition cursorToSpacedRepetition(Cursor cursor) {
        SpacedRepetition sRepetition = new SpacedRepetition();
        sRepetition.setId(cursor.getLong(0));
        sRepetition.setStage(cursor.getInt(1));
        sRepetition.setCycle(cursor.getInt(2));
        sRepetition.setLast_review(cursor.getLong(3));
        sRepetition.setNext_review(cursor.getLong(4));
        return sRepetition;
    }

    /** --------------- UPDATE --------------- **/

    public boolean updatePerformance(long wordId, int result) {
        ContentValues values = new ContentValues();
        long performanceId = getPerformanceId(wordId);
        Performance performance = getPerformance(performanceId);

        if (result == 0) {
            performance.incrementIncorrect();
        } else {
            performance.incrementCorrect();
        }

        values.put(MySQLiteHelper.COLUMN_CORRECT, performance.getCorrect());
        values.put(MySQLiteHelper.COLUMN_INCORRECT, performance.getIncorrect());

        int affectedRows = db.update(MySQLiteHelper.TABLE_PERFORMANCE, values,
                MySQLiteHelper.KEY_ID + " = " + performanceId, null);

        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateSpacedRepetition(long spacedRepetitionId, int stage, int cycle, long lastReview, long nextReview) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_STAGE, stage);
        values.put(MySQLiteHelper.COLUMN_CYCLE, cycle);
        values.put(MySQLiteHelper.COLUMN_LAST_REVIEW, lastReview);
        values.put(MySQLiteHelper.COLUMN_NEXT_REVIEW, nextReview);

        int affectedRows = db.update(MySQLiteHelper.TABLE_SPACED_REPETITION, values,
                MySQLiteHelper.KEY_ID + " = " + spacedRepetitionId, null);

        if (affectedRows > 0) return true;
        else return false;
    }

    public boolean updateUserNotification(long userId, int notify) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NOTIFY, notify);

        int affectedRows = db.update(MySQLiteHelper.TABLE_USER_NOTIFICATION, values,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null);

        if (affectedRows > 0) return true;
        else return false;
    }

    public boolean updateUser(long userId, String name, String username, String password) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);

        int affectedRows = db.update(MySQLiteHelper.TABLE_USER, values,
                MySQLiteHelper.KEY_ID + " = " + userId, null);

        if (affectedRows > 0) return true;
        else return false;
    }

    public boolean updateWord(long wordId, String name, String trans1, String trans2, String trans3) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_NAME, name);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION1, trans1);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION2, trans2);
        values.put(MySQLiteHelper.COLUMN_TRANSLATION3, trans3);

        int affectedRows = db.update(MySQLiteHelper.TABLE_WORD, values,
                MySQLiteHelper.KEY_ID + " = " + wordId, null);

        if (affectedRows > 0) return true;
        else return false;
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

    public boolean isNotificationActive(long userId) {
        Cursor cursor = db.query(MySQLiteHelper.TABLE_USER_NOTIFICATION, userNotificationColumns,
                MySQLiteHelper.COLUMN_USER_ID + " = " + userId, null, null, null, null, null);

        if (isCursorEmpty(cursor)) {
            cursor.close();
            return false;
        }

        cursor.moveToFirst();
        long active = cursor.getLong(1);

        if (active == 1) return true;
        else return false;
    }
}
