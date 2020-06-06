package com.example.starwapi.data;


import com.example.starwapi.presentation.model.SWPeople;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceRest {

    @GET("people/")
    Call<SWPeople> listPeople(@Query("page") int page);
}
