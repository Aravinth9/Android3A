package com.example.starwapi;

import com.example.starwapi.data.InterfaceRest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static InterfaceRest InterfaceRestInstance;



    public static Gson getGson() {

        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static InterfaceRest getInterfaceInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return InterfaceRestInstance = retrofit.create(InterfaceRest.class);
    }
}
