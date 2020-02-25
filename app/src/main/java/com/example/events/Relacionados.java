package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class Relacionados extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(pref.getBoolean("opcion1", false) && pref.getBoolean("opcion2", false)){
            setTheme(R.style.ambostheme);
        }else if(pref.getBoolean("opcion1", false)){
            setTheme(R.style.darktheme);
        }else if(pref.getBoolean("opcion2", false)){
            setTheme(R.style.goldtheme);
        }

        setContentView(R.layout.activity_relacionados);
        this.setTitle("Relacionados");
        ViewPager view = findViewById(R.id.view);
        view.setAdapter(new ListaEventosAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(view);

        Button btVolver = findViewById(R.id.volverRel);
        btVolver.setOnClickListener(this);

        Intent intent = getIntent();
        String lugar = intent.getStringExtra("lugar");
        String organizador = intent.getStringExtra("organizador");

        new LugarFragment(lugar);

        new OrganizadorFragment(organizador);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.volverRel:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
