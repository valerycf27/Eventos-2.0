package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetallesEvento extends AppCompatActivity implements View.OnClickListener {

    TextView tvLugarM;
    TextView tvOrganizadorM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_evento);
        this.setTitle(R.string.detallesEvento);

        Intent intent = getIntent();

        //ImageView imgImagen = findViewById(R.id.imgVEvento);
        TextView tvNombreM = findViewById(R.id.tvNombreM);
        tvLugarM = findViewById(R.id.tvLugarM);
        TextView tvFechaM = findViewById(R.id.tvFechaM);
        TextView tvHoraM = findViewById(R.id.tvHoraM);
        TextView tvAforoM = findViewById(R.id.tvAforoM);
        tvOrganizadorM = findViewById(R.id.tvOrganizadorM);
        TextView tvArtis_InvM = findViewById(R.id.tvArtis_InvM);
        TextView tvDescripcionM = findViewById(R.id.tvDescripcionM);
        TextView tvPrecioM = findViewById(R.id.tvPrecioM);
        CheckBox checkGuardar = findViewById(R.id.checkGuardar);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        Button btAnadirOpi = findViewById(R.id.btAnadirOpi);

        btAnadirOpi.setOnClickListener(this);

        /*byte[] byteArray = intent.getByteArrayExtra("imagen");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
         */

        String nombre = intent.getStringExtra("nombre");
        String lugar = intent.getStringExtra("lugar");
        String fecha = intent.getStringExtra("fecha");
        String hora = intent.getStringExtra("hora");
        long aforo = intent.getLongExtra("aforo", -1);
        String organizador = intent.getStringExtra("organizador");
        String artistas = intent.getStringExtra("artistas");
        String descripcion = intent.getStringExtra("descripcion");
        float precio = intent.getFloatExtra("precio", -1);
        float estrellas = intent.getFloatExtra("estrellas", -1);
        boolean guardado = intent.getBooleanExtra("guardado", false);


        //imgImagen.setImageBitmap(bmp);
        tvNombreM.setText(nombre);
        tvLugarM.setText(lugar);
        tvFechaM.setText(fecha);
        tvHoraM.setText(hora);
        tvAforoM.setText(String.valueOf(aforo));
        tvOrganizadorM.setText(organizador);
        tvArtis_InvM.setText(artistas);
        tvDescripcionM.setText(descripcion);
        tvPrecioM.setText(String.valueOf(precio));
        ratingBar.setRating(estrellas);
        checkGuardar.setChecked(guardado);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btAnadirOpi:
                TextView tvNombreM = findViewById(R.id.tvNombreM);
                Intent intent = new Intent(this,Anadir_Opinion.class);
                intent.putExtra("nombre", tvNombreM.getText().toString());
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_relacionados,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemRelacionados:
                Intent intentRelacionados = new Intent(this, Relacionados.class);
                intentRelacionados.putExtra("lugar", tvLugarM.getText().toString());
                intentRelacionados.putExtra("organizador", tvOrganizadorM.getText().toString());
                startActivity(intentRelacionados);
                break;
        }

        return true;
    }
}
