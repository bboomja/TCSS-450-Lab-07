package edu.tacoma.uw.reclycleviewpractice;

import java.io.Serializable;

public class Animal implements Serializable {
    private int mId;
    private String mName;
    private String mKind;

    public final static String ID = "id";
    public final static String KIND = "kind";
    public final static String NAME = "name";

    public Animal(int id, String name, String kind) {
        this.mId = id;
        this.mName = name;
        this.mKind = kind;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getKind() {
        return mKind;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setKind(String kind) {
        this.mKind = kind;
    }
}
