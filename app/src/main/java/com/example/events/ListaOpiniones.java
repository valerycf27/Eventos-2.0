package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListaOpiniones extends AppCompatActivity implements View.OnClickListener{
    private String eventoNom;
    TextView tvNombreM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_opiniones);
        this.setTitle(getString(R.string.titleListaOpi));
        Intent intent = getIntent();
        eventoNom = intent.getStringExtra("eventoNom");

        tvNombreM = findViewById(R.id.tvNombreM);
        tvNombreM.setText(eventoNom);
        Button btAnadirOpi = findViewById(R.id.btAnadirOpi);

        btAnadirOpi.setOnClickListener(this);
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
}
