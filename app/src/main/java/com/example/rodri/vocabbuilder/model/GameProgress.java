package com.example.rodri.vocabbuilder.model;

import android.app.Activity;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.example.rodri.vocabbuilder.database.MyDataSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by rodri on 2/14/2017.
 */

public class GameProgress implements Serializable{

    //private int numOfWords;
    private List<Long> wordsIds;
    private HashMap<Long, Integer> wordsResult;
    //private int numCorrect;
    //private int numIncorrect;
    private Game game;

    public GameProgress() {
        wordsIds = new ArrayList<>();
        wordsResult = new HashMap<>();
        game = new Game();
    }

    public GameProgress(List<Long> wordsIds, Activity activity) {
        game = new Game();
        setWordsIds(wordsIds);
    }

    public void setWordsIds(List<Long> wordsIds) {
        //this.numOfWords = wordsIds.size();
        this.game.setNumWords(wordsIds.size());
        this.wordsIds = wordsIds;
        initializeResults();
    }

    private void initializeResults() {
        this.wordsResult = new HashMap<>();
        for (int i = 0; i < wordsIds.size(); i++) {
            wordsResult.put(wordsIds.get(i), 0);
        }
    }


    public void setResult(long wordId, int res) {
        wordsResult.put(wordId, res);
    }

    public int getWordResult(long wordId) {
        return wordsResult.get(wordId);
    }

    public void calculateResult() {
        for (Map.Entry<Long, Integer> entry : wordsResult.entrySet()) {
            int res = entry.getValue();
            if (res == 1) {
                game.setCorrect(game.getCorrect()+1);
            } else {
                game.setIncorrect(game.getIncorrect()+1);
            }
            System.out.println("key: " + entry.getKey());
        }
    }

    public int getNumCorrect() {
        //return numCorrect;
        return game.getCorrect();
    }

    public int getNumIncorrect() {
        //return numIncorrect;
        return game.getIncorrect();
    }

    public int getNumOfWords() {
        //return numOfWords;
        return game.getNumWords();
    }

    public List<Long> getWordsIds() { return wordsIds; }

    public Game getGame() {
        return game;
    }

}
