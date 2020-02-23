package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListaOpiniones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_opiniones);
        this.setTitle(getString(R.string.titleListaOpi));
    }
}
