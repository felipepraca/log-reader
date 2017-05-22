package com.moip.logreader.model;

public class Count {

    private String value;
    private int count;

    public Count(String value) {
        this.value = value;
        this.count = 0;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        this.count++;
    }

    public String toString() {
        return value + " - " + count;
    };
}
