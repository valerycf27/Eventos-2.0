package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class Anadir_Opinion extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_anadir__opinion);
        this.setTitle(getString(R.string.titleAnadirOpi));

        Intent intent = getIntent();
        TextView tvNombreM = findViewById(R.id.tvNombreM);
        String nombre = intent.getStringExtra("nombre");
        tvNombreM.setText(nombre);



        Button btRestablecer = findViewById(R.id.btRestablecer);
        btRestablecer.setOnClickListener(this);

        Button btGuardar = findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btRestablecer:
                restablecer();
                break;
            case R.id.btGuardar:
                TextView tvNombreM = findViewById(R.id.tvNombreM);
                EditText txComentario = findViewById(R.id.txComentario);
                RatingBar ratingBar2 = findViewById(R.id.ratingBar2);

                Comentario comentario = new Comentario(tvNombreM.getText().toString(), txComentario.getText().toString(), ratingBar2.getRating());

                InsertaOpinion tarea = new InsertaOpinion(this, comentario);
                tarea.execute(Constantes.URL+"addComentario?");

                onBackPressed();
                break;
        }
    }

    private void restablecer() {
        EditText txComentario = findViewById(R.id.txComentario);
        RatingBar ratingBar2 = findViewById(R.id.ratingBar2);

        txComentario.setText("");
        ratingBar2.setRating(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_preferencias, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.preferencias:
                Intent intent = new Intent(this, PreferenciasActivity.class);
                startActivity(intent);

                break;
        }
        return true;
    }
}
