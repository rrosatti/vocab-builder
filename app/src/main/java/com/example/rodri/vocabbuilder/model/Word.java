package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 1/31/2017.
 */

public class Word {

    private long id;
    private String name;
    private String translation1;
    private String translation2;
    private String translation3;

    public Word() {}

    public Word(long id, String name, String translation1, String translation2, String translation3) {
        this.id = id;
        this.name = name;
        this.translation1 = translation1;
        this.translation2 = translation2;
        this.translation3 = translation3;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslation1() {
        return translation1;
    }

    public void setTranslation1(String translation1) {
        this.translation1 = translation1;
    }

    public String getTranslation2() {
        return translation2;
    }

    public void setTranslation2(String translation2) {
        this.translation2 = translation2;
    }

    public String getTranslation3() {
        return translation3;
    }

    public void setTranslation3(String translation3) {
        this.translation3 = translation3;
    }
}
