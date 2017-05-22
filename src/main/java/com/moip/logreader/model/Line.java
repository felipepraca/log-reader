package com.moip.logreader.model;

public class Line {

    private String url;
    private String status;

    public Line(String url, String status) {
        this.url = url;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }
}
