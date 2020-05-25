package com.example.myapplication;


import android.provider.Contacts;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterfaceRest {

    @GET("people/")
    Call<SWPeople> listPeople(@Query("page") int page);
}
