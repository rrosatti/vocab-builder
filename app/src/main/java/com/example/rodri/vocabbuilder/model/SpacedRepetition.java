package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 2/26/2017.
 */

public class SpacedRepetition {

    private long id;
    private int stage;
    private int cycle;
    private long last_review;
    private long next_review;

    public SpacedRepetition() {}

    public SpacedRepetition(long id, int stage, int cycle, long last_review, long next_review) {
        this.id = id;
        this.stage = stage;
        this.cycle = cycle;
        this.last_review = last_review;
        this.next_review = next_review;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getCycle() {
        return cycle;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public long getLast_review() {
        return last_review;
    }

    public void setLast_review(long last_review) {
        this.last_review = last_review;
    }

    public long getNext_review() {
        return next_review;
    }

    public void setNext_review(long next_review) {
        this.next_review = next_review;
    }
}
