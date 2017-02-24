package com.example.rodri.vocabbuilder.model;

import java.io.Serializable;

/**
 * Created by rodri on 2/23/2017.
 */

public class Game implements Serializable {

    private long id;
    private int numWords;
    private int correct;
    private int incorrect;
    private long addedAt;

    public Game() {}

    public Game(long id, int numWords, int correct, int incorrect, long addedAt) {
        this.id = id;
        this.numWords = numWords;
        this.correct = correct;
        this.incorrect = incorrect;
        this.addedAt = addedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumWords() {
        return numWords;
    }

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public long getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(long addedAt) {
        this.addedAt = addedAt;
    }
}
