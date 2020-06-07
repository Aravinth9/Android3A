package com.example.starwapi.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.starwapi.Constants;
import com.example.starwapi.R;
import com.example.starwapi.Singletons;
import com.example.starwapi.presentation.model.Personnage;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        txtDetail = findViewById(R.id.detail_txt);

        Intent intent = getIntent();
        String test = intent.getStringExtra(Constants.KEY_DETAIL);
        Log.d("ARAV onCreate De", test);
        String persoJson = intent.getStringExtra(Constants.KEY_DETAIL);
        Personnage perso = Singletons.getGson().fromJson(persoJson,Personnage.class);
        showDetails(perso);

    }

    private void showDetails(Personnage perso) {
        txtDetail.setText(perso.getName());
    }
}
