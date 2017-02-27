package com.example.rodri.vocabbuilder.model;

import com.example.rodri.vocabbuilder.util.DateUtil;

import java.io.Serializable;

/**
 * Created by rodri on 2/26/2017.
 */

public class SpacedRepetition {

    private long id;
    private int stage;
    private int cycle;
    private long last_review;
    private long next_review;
    private DateUtil dateUtil = new DateUtil();

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

    public void update(long result) {
        last_review = dateUtil.getCurrentDate();
        switch (stage) {
            case 1: {
                // plus 1 day
                if (result == 1) {
                    next_review = dateUtil.plusDays(last_review, 1);
                } else {
                    next_review = dateUtil.plusDays(last_review, 1);
                }
                break;
            }
            case 2: {
                // plus 10 days
                if (result == 1) {
                    next_review = dateUtil.plusDays(last_review, 10);
                } else {
                    next_review = dateUtil.plusDays(last_review, 1);
                }
                break;
            }
            case 3: {
                // plus 20 days
                if (result == 1) {
                    next_review = dateUtil.plusDays(last_review, 20);
                } else {
                    next_review = dateUtil.plusDays(last_review, 5);
                }
                break;
            }
            case 4: {
                // plus 30 days
                if (result == 1) {
                    next_review = dateUtil.plusDays(last_review, 30);
                } else {
                    next_review = dateUtil.plusDays(last_review, 10);
                }
                break;
            }
            case 5: {
                // back to stage 2 (advance 10 days?) and cycle++
                if (result == 1) {
                    next_review = dateUtil.plusDays(last_review, 10);
                    cycle++;
                    stage = 1; // = 1 because after the switch-case finishes, the stage variable will be incremented
                } else {
                    next_review = dateUtil.plusDays(last_review, 10);
                }
                break;
            }
        }
        // advance to next stage if user was right
        if (result == 1) {
            stage++;
        }

    }
}
