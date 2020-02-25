package com.example.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListaOpiniones extends AppCompatActivity implements View.OnClickListener{
    private String eventoNom;
    TextView tvNombreM;
    public static ArrayList<Comentario> opiniones = new ArrayList<>();
    public static ComentarioAdapter adaptador;

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

        setContentView(R.layout.activity_lista_opiniones);
        this.setTitle(getString(R.string.titleListaOpi));

        ListView lvOpinion = findViewById(R.id.lvEventosMain);
        adaptador = new ComentarioAdapter(this, opiniones);
        lvOpinion.setAdapter(adaptador);

        Intent intent = getIntent();
        eventoNom = intent.getStringExtra("eventoNom");

        tvNombreM = findViewById(R.id.tvNombreM);
        tvNombreM.setText(eventoNom);
        Button btAnadirOpi = findViewById(R.id.btAnadirOpi);

        btAnadirOpi.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();

        opiniones.clear();

        adaptador.notifyDataSetChanged();

        cargarListaOpiniones();

    }

    private void cargarListaOpiniones() {

        DescargaDatos tarea = new DescargaDatos(this, opiniones, "Lista Opiniones");
        tarea.execute(Constantes.URL+"comentariosNombre?nombreEvento="+eventoNom);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btAnadirOpi:

                Intent intent = new Intent(this,Anadir_Opinion.class);
                intent.putExtra("nombre", tvNombreM.getText().toString());
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
