package com.example.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Formulario_evento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener ,TimePickerDialog.OnTimeSetListener{

    TextView tvFecha;
    TextView tvHora;
    private final int FOTO_TAREA = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_evento);
        this.setTitle(R.string.nuevoEvento);

        ImageView imvFotoFormulario = findViewById(R.id.imvFotoFormulario);
        imvFotoFormulario.setOnClickListener(this);

        tvFecha = findViewById(R.id.tvFecha);//donde se va a ver la fecha elegida
        Button btFecha = findViewById(R.id.btFecha);
        btFecha.setOnClickListener(this);

        tvHora = findViewById(R.id.tvHora);//donde se va a ver la hora elegida;
        Button btHora = findViewById(R.id.btHora);
        btHora.setOnClickListener(this);

        Button btRestablecer = findViewById(R.id.btRestablecer);
        btRestablecer.setOnClickListener(this);

        Button btGuardar = findViewById(R.id.btGuardar);
        btGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btFecha:
               mostrarEleccionFecha();
               break;
            case R.id.btHora:
                mostrarEleccionHora();
                break;
            case R.id.btRestablecer:
                restablecer();
                break;
            case R.id.imvFotoFormulario:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, FOTO_TAREA);
                break;
            case R.id.btGuardar:

                ImageView imvFotoFormulario = findViewById(R.id.imvFotoFormulario);
                //aquí es donde me dice que imvFotoFormularioes nulo
                Bitmap imagen = ((BitmapDrawable) imvFotoFormulario.getDrawable()).getBitmap();


                EditText txNomEvento = findViewById(R.id.txNomEvento);
                EditText txLugar = findViewById(R.id.txLugar);
                EditText txAforo = findViewById(R.id.txAforo);
                EditText txOrganizador = findViewById(R.id.txOrganizador);
                EditText txPrecio = findViewById(R.id.txPrecio);
                EditText txArtistas = findViewById(R.id.txArtistas);
                EditText txDescripcion = findViewById(R.id.txDescripcion);

                String nombre = txNomEvento.getText().toString();
                if (nombre.equals("")) {
                    Toast.makeText(this, getString(R.string.nombreObligatorio), Toast.LENGTH_LONG).show();
                    return;
                }
                String lugar = txLugar.getText().toString();
                if (lugar.equals("")){
                    lugar=getString(R.string.desconocido);
                }
                String fecha = tvFecha.getText().toString();
                if (fecha.equals("")){
                    fecha= getString(R.string.desconocida);
                }
                String hora = tvHora.getText().toString();
                if (hora.equals("")){
                    hora=getString(R.string.desconocida);
                }
                long  aforo=0;
                try{
                    aforo= Long.parseLong(txAforo.getText().toString());
                }catch (NumberFormatException nfe) {
                    Toast.makeText(this, getString(R.string.aforoTieneQueSerNumero), Toast.LENGTH_LONG).show();
                    return;
                }
                if (aforo<=0){
                    Toast.makeText(this, getString(R.string.aforoAlMenosUno), Toast.LENGTH_LONG).show();
                    return;
                }
                String organizador = txOrganizador.getText().toString();
                String artistasInvitados = txArtistas.getText().toString();
                String descripcion = txDescripcion.getText().toString();
                float precio=0;
                try{
                    precio = Float.parseFloat(txPrecio.getText().toString());
                }catch (NumberFormatException nfe) {
                    Toast.makeText(this, getString(R.string.precioTieneQueSerNumero), Toast.LENGTH_LONG).show();
                    return;
                }
                if(precio<0){
                    Toast.makeText(this, getString(R.string.precioNoNegativo), Toast.LENGTH_LONG).show();
                    return;
                }
                float estrellas = 0; //ya que la valoración la deberían dar los usuarios
                boolean guardado=false; //no se guarda al crearse, eso se hace luego


                Evento evento = new Evento(nombre, lugar, fecha, hora, aforo, organizador, artistasInvitados, descripcion, precio, estrellas, guardado);
                //evento.setImagen(imagen);
                evento.setLatitud(40.453005f);
                evento.setLongitud(-3.6884557f);
                //DataBase db = new DataBase(this);
                //db.nuevoEvento(evento);

                TareaAnadeEvento tareaAnadeEvento = new TareaAnadeEvento(this, evento);
                tareaAnadeEvento.execute(Constantes.URL+"anadirEvento");

                onBackPressed();
                break;
        }
    }

    public void mostrarEleccionFecha(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia){
        String fecha = dia+"/"+(mes+1)+"/"+ano;
        tvFecha.setText(fecha);

    }

    public void mostrarEleccionHora(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                        android.text.format.DateFormat.is24HourFormat(this)
        );
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int h, int min){
        String hora = h+":"+min;
        tvHora.setText(hora);
    }

    public void restablecer(){
        EditText txNomEvento = findViewById(R.id.txNomEvento);
        EditText txLugar = findViewById(R.id.txLugar);
        EditText txAforo = findViewById(R.id.txAforo);
        EditText txOrganizador = findViewById(R.id.txOrganizador);
        EditText txPrecio = findViewById(R.id.txPrecio);
        EditText txArtistas = findViewById(R.id.txArtistas);
        EditText txDescripcion = findViewById(R.id.txDescripcion);

        txNomEvento.setText("");
        txLugar.setText("");
        tvFecha.setText("");
        tvHora.setText("");
        txAforo.setText("");
        txOrganizador.setText("");
        txPrecio.setText("");
        txArtistas.setText("");
        txDescripcion.setText("");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == FOTO_TAREA) && (resultCode == RESULT_OK)
                && (data != null)) {
            // Obtiene el Uri de la imagen seleccionada por el usuario
            Uri imagenSeleccionada = data.getData();
            String[] ruta = {MediaStore.Images.Media.DATA };

            // Realiza una consulta a la galería de imágenes solicitando la imagen seleccionada
            Cursor cursor = getContentResolver().query(imagenSeleccionada, ruta, null, null, null);
            cursor.moveToFirst();

            // Obtiene la ruta a la imagen
            int indice = cursor.getColumnIndex(ruta[0]);
            String picturePath = cursor.getString(indice);
            cursor.close();

            // Carga la imagen en una vista ImageView que se encuentra en
            // en layout de la Activity actual
            ImageView imageView = (ImageView) findViewById(R.id.imvFotoFormulario);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


}
