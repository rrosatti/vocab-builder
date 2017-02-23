package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 2/6/2017.
 */

public class GameLog {

    private long id;
    private long gameId;
    private long wordId;
    private int result;

    public GameLog() {}

    public GameLog(long id, long gameId, long wordId, int result) {
        this.id = id;
        this.gameId = gameId;
        this.wordId = wordId;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameId() {
        return gameId;
    }
}
