package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class Anadir_Opinion extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir__opinion);

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
                //TODO guardar comentario y valoración en Spring
                //restablecer();
                break;
        }
    }

    private void restablecer() {
        EditText txComentario = findViewById(R.id.txComentario);
        RatingBar ratingBar2 = findViewById(R.id.ratingBar2);

        txComentario.setText("");
        ratingBar2.setRating(0);
    }
}
