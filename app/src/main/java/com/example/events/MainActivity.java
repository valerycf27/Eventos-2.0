package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

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

        setContentView(R.layout.activity_main);
        this.setTitle(R.string.ListaEventos);

        eventos = new ArrayList<>();
        ListView lvEventosMain = findViewById(R.id.lvEventosMain);
        adaptador = new EventoAdapter(this, eventos);
        lvEventosMain.setAdapter(adaptador);
        lvEventosMain.setOnItemClickListener(this);

        Button btNuevo = findViewById(R.id.btNuevo);
        btNuevo.setOnClickListener(this);
        Button btGuardados = findViewById(R.id.btGuardados);
        btGuardados.setOnClickListener(this);

        registerForContextMenu(lvEventosMain);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
*/
        //DataBase db = new DataBase(this);
        eventos.clear();
        //eventos.addAll(db.getEventos());
        adaptador.notifyDataSetChanged();

        DescargaDatos descargaDatos = new DescargaDatos (this,eventos, "main lista");
        descargaDatos.execute(Constantes.URL+"eventos");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btNuevo:
                Intent intent = new Intent(this, Formulario_evento.class);
                startActivity(intent);
                break;

            case R.id.btGuardados:
                Intent intentG = new Intent(this, EventosGuardados.class);
                startActivity(intentG);
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual_eventos, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int posicion = menuInfo.position;

        switch (item.getItemId()) {
            case R.id.itemGuardar:
                Evento evento = eventos.get(posicion);
                evento.guardar();
                DataBase db = new DataBase(this);
                db.guardarEvento(evento);
                adaptador.notifyDataSetChanged();
                return true;

            case R.id.itemDescartar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.Confirm).setTitle(R.string.eliminarEvento)
                        .setPositiveButton(R.string.si,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Qué hacer si el usuario pulsa "Si"
                                        Evento eventoEliminado = eventos.remove(posicion);

                                        DataBase db = new DataBase(getApplicationContext());
                                        db.descartarEvento(eventoEliminado);
                                        adaptador.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }})
                        .setNegativeButton(R.string.no,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Qué hacer si el usuario pulsa "No"
                                        // En este caso se cierra directamente el diálogo y no se hace nada más
                                        dialog.dismiss();
                                    }});

                builder.create().show();
                return true;

            case R.id.verMapa:
                Intent intentMapa = new Intent(this, Mapa.class);

                intentMapa.putExtra("eventoLat", eventos.get(posicion).getLatitud());
                intentMapa.putExtra("eventoLong", eventos.get(posicion).getLongitud());
                intentMapa.putExtra("eventoNom", eventos.get(posicion).getNombre());
                intentMapa.putExtra("eventoLug", eventos.get(posicion).getLugar());

                startActivity(intentMapa);
                return true;

            case R.id.verOpiniones:
                Intent intent = new Intent(this,ListaOpiniones.class);
                intent.putExtra("eventoNom", eventos.get(posicion).getNombre());
                startActivity(intent);

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.action_anadir_evento,menu);
        inflater.inflate(R.menu.menu_preferencias, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.accionDetalles:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.acercaV2Mensaje).setTitle(R.string.acercaEventosv2)
                        .setNegativeButton(R.string.cerrar,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }});

                builder.create().show();
                break;
            case R.id.accionNuevoEvento:
                Intent intent = new Intent(this, Formulario_evento.class);
                startActivity(intent);
                break;
            case R.id.preferencias:
                intent = new Intent(this, PreferenciasActivity.class);
                startActivity(intent);

                break;

        }


        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
        Intent intentMapa = new Intent(this, DetallesEvento.class);

        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
        eventos.get(i).getImagen().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        intentMapa.putExtra("imagen", byteArray);*/

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
}
