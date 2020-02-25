package com.example.events;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;


public class InsertaOpinion extends AsyncTask<String, Void, Void> {
    Comentario comentario;
    Activity act;

    public InsertaOpinion(Activity act, Comentario comentario) {
        this.act = act;
        this.comentario = comentario;
    }

    @Override
    protected Void doInBackground(String... urlParam) {


        String nombreEvento = this.comentario.getNombreEvento(), contenido = this.comentario.getContenido();
        float valoracion = this.comentario.getValoracion();

        //try {
        // Conecta con la URL y obtenemos el fichero con los datos
        URL url;
        try {
            url = new URL(urlParam[0] + "nombreEvento=" + nombreEvento + "&contenido=" + contenido +
                    "&valoracion=" + valoracion);

            Log.e("URL", url.toString());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getForObject(url.toString(), Void.class);
        } catch (Exception e) {
            Log.d("VAL ANADE OPI ERROR", e.getMessage());
        }

        return null;
    }

}

