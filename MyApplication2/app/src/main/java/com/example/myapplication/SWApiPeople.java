package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SWApiPeople {
    @GET("/people/1/")
    Call<RestSWPeopleResponse> getSWPeopleResponse();
}
