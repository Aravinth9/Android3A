package com.example.myapplication;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        makeApiCall();
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

    private static final String BASE_URL = "http://swapi.dev/api/";

    private void makeApiCall()
    {
        int pageIndex = 1;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        InterfaceRest SWService = retrofit.create(InterfaceRest.class);
        //PokeApi pokeAPI = retrofit.create(PokeApi.class);
        Call<SWPeople> peopleRequest = SWService.listPeople(pageIndex);
        peopleRequest.enqueue(new Callback<SWPeople>() {
            @Override
            public void onResponse(Call<SWPeople> call, Response<SWPeople> response) {
                if (!response.isSuccessful()){
                    Log.d("ARAVINTH", "onResponse: ERREUR" + String.valueOf(response.code()));
                }
                else{
                    List<Personnage> p = response.body().results;
                    showList(p);
                    Log.d("ARAVINTH", "onResponse: ICI");
                }
            }

            @Override
            public void onFailure(Call<SWPeople> call, Throwable t) {
                Log.d("SWAPI", "Request Fail. Error: " + t.getMessage());
            }
        });
        /*
        Call<RestPokeResponse> call = pokeAPI.getPokemonResponse();
        call.enqueue(new Callback<RestPokeResponse>() {
            @Override
            public void onResponse(Call<RestPokeResponse> call, Response<RestPokeResponse> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    List<Pokemon> pokemonList = response.body().getResults();
                    Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }
                else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokeResponse> call, Throwable t) {
                showError();
            }
        });

         */
    }

    private void showError()
    {
        Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show();
    }
}
