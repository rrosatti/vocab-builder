package com.example.rodri.vocabbuilder.model;

/**
 * Created by rodri on 1/31/2017.
 */

public class Language {

    private long id;
    private String name;

    public Language() {}

    public Language(long id, String name) {
        this.id = id;
        this.name = name;
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
}
