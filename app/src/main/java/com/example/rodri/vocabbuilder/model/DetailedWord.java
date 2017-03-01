package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 2/4/2017.
 */

public class DetailedWord {

    private Word word;
    private Language language;
    private Performance performance;
    private SpacedRepetition sRepetition;

    public DetailedWord() {
        word = new Word();
        language = new Language();
        performance = new Performance();
        sRepetition = new SpacedRepetition();
    }

    public DetailedWord(Word word, Language language, Performance performance, SpacedRepetition sRepetition) {
        this.word = word;
        this.language = language;
        this.performance = performance;
        this.sRepetition = sRepetition;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public SpacedRepetition getSpacedRepetition() {
        return sRepetition;
    }

    public void setSpacedRepetition(SpacedRepetition sRepetition) {
        this.sRepetition = sRepetition;
    }
}
