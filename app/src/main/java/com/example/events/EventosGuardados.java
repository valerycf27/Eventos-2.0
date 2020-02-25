package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class EventosGuardados extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Evento> eventos;
    public static EventoAdapter adaptador;

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

        setContentView(R.layout.activity_eventos_guardados);
        this.setTitle(R.string.eventosGuardados);
        eventos = new ArrayList<>();
        ListView lvEventosGuardados = findViewById(R.id.lvEventosGuardados);
        adaptador = new EventoAdapter(this, eventos);
        lvEventosGuardados.setAdapter(adaptador);
        lvEventosGuardados.setOnItemClickListener(this);

        registerForContextMenu(lvEventosGuardados);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //DataBase db = new DataBase(this);
        eventos.clear();
        //eventos.addAll(db.getGuardados());
        adaptador.notifyDataSetChanged();

        DescargaDatos descargaDatos = new DescargaDatos (this,eventos, "lista guardados");
        descargaDatos.execute(Constantes.URL+"eventosGuardados?guardado=true");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        Intent intentMapa = new Intent(this, DetallesEvento.class);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        eventos.get(i).getImagen().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intentMapa.putExtra("imagen", byteArray);

        intentMapa.putExtra("nombre", eventos.get(i).getNombre());
        intentMapa.putExtra("fecha", eventos.get(i).getFecha());
        intentMapa.putExtra("hora", eventos.get(i).getHora());
        intentMapa.putExtra("lugar", eventos.get(i).getLugar());
        intentMapa.putExtra("aforo", eventos.get(i).getAforo());
        intentMapa.putExtra("organizador", eventos.get(i).getOrganizador());
        intentMapa.putExtra("artistas", eventos.get(i).getArtistasInvitados());
        intentMapa.putExtra("descripcion", eventos.get(i).getDescripcion());
        intentMapa.putExtra("precio", eventos.get(i).getPrecio());
        intentMapa.putExtra("estrellas", eventos.get(i).getEstrellas());
        intentMapa.putExtra("guardado", eventos.get(i).isGuardado());

        startActivity(intentMapa);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
