package com.moip.logreader.model;

import java.util.List;

public class Result {

    private List<Count> topUrls;
    private List<Count> statusCount;

    public Result(List<Count> topUrls, List<Count> statusCount) {
        this.topUrls = topUrls;
        this.statusCount = statusCount;
    }

    public List<Count> getTopUrls() {
        return topUrls;
    }

    public List<Count> getStatusCount() {
        return statusCount;
    }

}
