package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

        showList();
        makeApiCall();
    }

    private void showList() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new ListeAdapter(input);
        recyclerView.setAdapter(mAdapter);
    }

    private static final String BASE_URL = "https://swapi.dev/api/";

    private void showError()
    {
        Toast.makeText(this, "API Error", Toast.LENGTH_SHORT).show();
    }

    private void makeApiCall() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SWApiPeople swPeople = retrofit.create(SWApiPeople.class);
        PokeApi pokeAPI = retrofit.create(PokeApi.class);

        Call<RestSWPeopleResponse> call = swPeople.getSWPeopleResponse();
        call.enqueue(new Callback<RestSWPeopleResponse>() {
            @Override
            public void onResponse(Call<RestSWPeopleResponse> call, Response<RestSWPeopleResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String people1 = response.body().getBirth_year();
                    Toast.makeText(getApplicationContext(), people1, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestSWPeopleResponse> call, Throwable t) {
                showError();
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
    }
    */


    }
}
