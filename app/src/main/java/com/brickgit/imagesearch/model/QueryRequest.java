package com.brickgit.imagesearch.model;

/**
 * Created by Daniel Lin on 7/2/16.
 */
public class QueryRequest {

    public static QueryRequest getNewRequest(String query) {
        return new QueryRequest(query, 1);
    }

    private final String query;
    private int page;

    private QueryRequest(String query, int page) {
        this.query = query;
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public int getPage() {
        return page;
    }

    public void nextPage() {
        this.page++;
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QueryRequest) {
            QueryRequest request = (QueryRequest) that;
            return query.equals(request.query) && page == request.page;
        }
        return false;
    }
}
