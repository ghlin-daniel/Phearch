package com.guanhaolin.pearch.model;

import java.util.List;

public class QueryResponse<T> {

  private int total;
  private int totalHits;
  private List<T> hits;

  public QueryResponse() {}

  public int getTotal() {
    return total;
  }

  public int getTotalHits() {
    return totalHits;
  }

  public List<T> getHits() {
    return hits;
  }
}
