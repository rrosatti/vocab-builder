package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 1/31/2017.
 */

public class Performance {

    private long id;
    private int correct;
    private int incorrect;

    public Performance() {}

    public Performance(long id, int correct, int incorrect) {
        this.id = id;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getCorrect() {
        return correct;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void incrementCorrect() {
        correct++;
    }

    public void incrementIncorrect() {
        incorrect++;
    }
}
