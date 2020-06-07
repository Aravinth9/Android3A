package com.example.starwapi;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.starwapi.data.InterfaceRest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static InterfaceRest InterfaceRestInstance;
    private static SharedPreferences sharedPreferencesInstance;



    public static Gson getGson() {

        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static InterfaceRest getInterfaceInstance() {
        if (InterfaceRestInstance == null){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceRestInstance = retrofit.create(InterfaceRest.class);
        }
        return InterfaceRestInstance;
    }

    public static SharedPreferences getsharedPreferencesInstance(Context context){
        if( sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences(Constants.KEY_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
