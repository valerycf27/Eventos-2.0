package com.example.events;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.net.URL;


public class TareaAnadeEvento extends AsyncTask<String, Void, Void> {
    Evento evento;
    Activity act;

    public TareaAnadeEvento(Activity act, Evento evento) {
        this.act = act;
        this.evento = evento;
    }

    @Override
    protected Void doInBackground(String... urlParam) {


        String nombre = evento.getNombre(), lugar = evento.getLugar(),
                fecha = evento.getFecha(),hora = evento.getHora(), organizador = evento.getOrganizador()
                , artistasInvitados = evento.getArtistasInvitados(), descripcion = evento.getDescripcion();
        boolean guardado  = evento.isGuardado();
        float precio  = evento.getPrecio(), estrellas  = evento.getEstrellas(), latitud  = evento.getLatitud(), longitud  = evento.getLongitud();
        long aforo = evento.getAforo();


        try {
            // Conecta con la URL y obtenemos el fichero con los datos
            Log.d("DAVID ANADE TAREA", "URL = "+urlParam[0]);
            URL url = new URL(urlParam[0]);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getForObject(url + "?nombre=" + nombre + "&lugar=" + lugar +
                    "&fecha=" + fecha + "&hora=" + hora + "&aforo=" + aforo +
                    "&organizador=" + organizador + "&artistasInvitados=" + artistasInvitados + "&descripcion=" + descripcion +
                    "&precio=" + precio + "&estrellas=" + estrellas + "&guardado=" + guardado+ "&latitud=" + latitud+
                    "&longitud=" + longitud, Void.class);
        } catch (Exception e) {
            Log.d("DAVID ANADE TAREA ERROR", e.getMessage());
        }

        return null;
    }

}
