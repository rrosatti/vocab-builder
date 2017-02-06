package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 2/6/2017.
 */

public class PlayingLog {

    private long id;
    private long wordId;
    private int result;
    private long addedAt;

    public PlayingLog() {}

    public PlayingLog(long id, long wordId, int result, long addedAt) {
        this.id = id;
        this.wordId = wordId;
        this.result = result;
        this.addedAt = addedAt;
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

    public long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(long addedAt) {
        this.addedAt = addedAt;
    }
}
