package com.example.starwapi.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starwapi.Constants;
import com.example.starwapi.R;
import com.example.starwapi.Singletons;
import com.example.starwapi.data.InterfaceRest;
import com.example.starwapi.presentation.model.Personnage;
import com.example.starwapi.presentation.model.SWPeople;
import com.example.starwapi.presentation.view.ListeAdapter;
import com.example.starwapi.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private Gson gson;
    private SharedPreferences sharedPreferences;

    private MainActivity view;

    public MainController(MainActivity mainActivity, SharedPreferences sharedPreferences, Gson gson) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        List<Personnage> personnageList = getDataFromCache();
        if( personnageList != null) {
            view.showList(personnageList);
        }else {
            makeApiCall();
        }

    }

    private void saveList(List<Personnage> personnageList) {

        String jsonString = gson.toJson(personnageList);
        sharedPreferences.edit().putString(Constants.KEY_LIST, jsonString).apply();

        Log.d("ARAVINTH", "Request Fail. Error: " + jsonString);

    }



    private void makeApiCall()
    {
        int pageIndex = 1;
        Call<SWPeople> peopleRequest = Singletons.getInterfaceInstance().listPeople(pageIndex);
        peopleRequest.enqueue(new Callback<SWPeople>() {
            @Override
            public void onResponse(Call<SWPeople> call, Response<SWPeople> response) {
                if (!response.isSuccessful()){
                    Log.d("ARAVINTH", "onResponse: ERREUR" + String.valueOf(response.code()));
                }
                else{
                    List<Personnage> personnageList = response.body().results;

                    saveList(personnageList);

                    view.showList(personnageList);
                }
            }

            @Override
            public void onFailure(Call<SWPeople> call, Throwable t) {
                Log.d("ARAVINTH", "Request Fail. Error: " + t.getMessage());
            }
        });

    }


    private List<Personnage> getDataFromCache() {
        String jsonPerso = sharedPreferences.getString(Constants.KEY_LIST, null);
        if(jsonPerso == null){
            return null;
        } else {
            Type ListType = new TypeToken<List<Personnage>>(){}.getType();
            return gson.fromJson(jsonPerso, ListType);
        }

    }

    public void onItemClick(Personnage perso){
        view.navigateToDetails(perso);
    }

    public void onButtonClick() {

    }
}
