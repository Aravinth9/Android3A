package com.example.starwapi;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import androidx.recyclerview.widget.RecyclerView;

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
        List<Personnage> SWPerso;
        showList();
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

                    //showList(personnageList);
                }
            }

            @Override
            public void onFailure(Call<SWPeople> call, Throwable t) {
                Log.d("ARAVINTH", "Request Fail. Error: " + t.getMessage());
            }
        });

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
        }
        mAdapter = new ListeAdapter(input);
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
