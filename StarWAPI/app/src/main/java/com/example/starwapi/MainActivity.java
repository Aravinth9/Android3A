package com.example.starwapi;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = getSharedPreferences("starwars", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        /*
        List<Personnage> personnageList = getDataFromCache();
        if(personnageList != null) {
            showList(personnageList);
        }else {
            makeApiCall();
        }


         */
        makeApiCall();

    }

    private static final String BASE_URL = "https://swapi.dev/api/";
    private void makeApiCall()
    {
        int pageIndex = 1;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        InterfaceRest SWService = retrofit.create(InterfaceRest.class);
        Call<SWPeople> peopleRequest = SWService.listPeople(pageIndex);
        peopleRequest.enqueue(new Callback<SWPeople>() {
            @Override
            public void onResponse(Call<SWPeople> call, Response<SWPeople> response) {
                if (!response.isSuccessful()){
                    Log.d("ARAVINTH", "onResponse: ERREUR" + String.valueOf(response.code()));
                }
                else{
                    List<Personnage> personnageList = response.body().results;

                    saveList(personnageList);

                    showList(personnageList);
                }
            }

            @Override
            public void onFailure(Call<SWPeople> call, Throwable t) {
                Log.d("ARAVINTH", "Request Fail. Error: " + t.getMessage());
            }
        });

    }


    private List<Personnage> getDataFromCache() {
        String jsonPerso = sharedPreferences.getString("jsonPersoList", null);
        if(jsonPerso == null){
            return null;
        } else {
            Type ListType = new TypeToken<List<Personnage>>(){}.getType();
            return gson.fromJson(jsonPerso, ListType);
        }

    }

    private void saveList(List<Personnage> personnageList) {

        String jsonString = gson.toJson(personnageList);
        sharedPreferences.edit().putString("jsonPersoList", jsonString).apply();

        Log.d("ARAVINTH", "Request Fail. Error: " + jsonString);

    }

    private void showList(List<Personnage> SWPerso) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListeAdapter(SWPerso);
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
