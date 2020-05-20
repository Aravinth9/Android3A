package com.example.myapplication;

import java.util.List;

public class RestPokeResponse {
    private int count;
    private String next;
    private List<Pokemon> results;

    public int getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<Pokemon> getResults() {
        return results;
    }


}
