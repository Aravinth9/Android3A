package com.example.starwapi.presentation.view;
import android.content.Intent;
import android.os.Bundle;

import com.example.starwapi.Constants;
import com.example.starwapi.Singletons;
import com.example.starwapi.R;
import com.example.starwapi.presentation.controller.MainController;
import com.example.starwapi.presentation.model.Personnage;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MainController controller;
    private RecyclerView recyclerView;
    private ListeAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button liste;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        controller = new MainController(
                this
                ,Singletons.getsharedPreferencesInstance(getApplicationContext()),
                Singletons.getGson()
        );

        start();

    }

    public void start(){

        liste = findViewById(R.id.button);
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                        Je sais c'est mal écrit mais j'y ai pensé au dernier moment. Désolé !

                */
                setContentView(R.layout.activity_main);
                controller.onStart();
            }
        });

    }

    public void showList(List<Personnage> SWPerso) {

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mAdapter = new ListeAdapter(SWPerso, new ListeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Personnage perso) {
                controller.onItemClick(perso);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void navigateToDetails(Personnage perso) {
        Intent intent = new Intent (MainActivity.this, DetailActivity.class);

        String test = Singletons.getGson().toJson(perso);
        Log.d("ARAV navigate t", test);
        intent.putExtra(Constants.KEY_DETAIL,Singletons.getGson().toJson(perso));
        MainActivity.this.startActivity(intent);

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
