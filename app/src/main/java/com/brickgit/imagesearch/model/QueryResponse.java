package com.brickgit.imagesearch.model;

import java.util.List;

/**
 * Created by Daniel Lin on 6/30/16.
 */
public class QueryResponse {

    private int total;
    private int totalHits;
    private List<ImageInfoResponse> hits;

    public QueryResponse() {}

    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<ImageInfoResponse> getHits() {
        return hits;
    }
}
