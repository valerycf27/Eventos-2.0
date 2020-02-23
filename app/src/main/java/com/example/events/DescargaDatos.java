package com.example.events;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class DescargaDatos extends AsyncTask<String, Void, Void> {
    private boolean error = false;
    private ProgressDialog dialog;
    private Activity act;
    ArrayList arrListaEventos;
    //private String procedencia;

    public DescargaDatos(Activity act, ArrayList arrListeventos) {
        this.act = act;
        this.arrListaEventos = arrListeventos;
        //this.procedencia = procedencia.toLowerCase();
    }

    @Override
    protected Void doInBackground(String... params) {

        String url = params[0];
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Evento[] eventosArray = restTemplate.getForObject(url, Evento[].class);
            arrListaEventos.addAll(Arrays.asList(eventosArray));
            /*switch (procedencia) {

                case "lista opiniones":
                    Opinion[] opinionesArray = restTemplate.getForObject(url, Opinion[].class);
                    arrListaEventos.addAll(Arrays.asList(opinionesArray));
                    break;
                case "lista tiendas mapa":
                    Tienda[] tiendasArray = restTemplate.getForObject(url, Tienda[].class);
                    arrListaEventos.addAll(Arrays.asList(tiendasArray));
                    break;

                case "lista principal":

                case "lista tab desarrolladora":

                case "lista tab plataforma":

                case "lista tab tienda":

                case "lista tab genero":
                    Videojuego[] videojuegosArray = restTemplate.getForObject(url, Videojuego[].class);
                    arrListaEventos.addAll(Arrays.asList(videojuegosArray));
                    break;

                default:
                    Log.e("LLAMADA NO ENCONTRADA", "Se ha llamado a la descarga de datos desde un lugar no controlado");
                    break;
            }*/
        } catch (Exception e) {
            Log.e("DAVID ERROR", e.getMessage());
            error = true;
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        arrListaEventos = new ArrayList<>();
    }

    @Override
    protected void onProgressUpdate(Void... progreso) {
        super.onProgressUpdate(progreso);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(act);
        dialog.setTitle("Cargando");
        dialog.show();
    }


    @Override
    protected void onPostExecute(Void resultado) {
        super.onPostExecute(resultado);

        if (error) {
            Toast.makeText(act, "Error con los datos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dialog != null)
            dialog.dismiss();

        MainActivity.adaptador.notifyDataSetChanged();
        /*switch (procedencia) {

            case "lista opiniones":
                ListaOpiniones.adaptador.notifyDataSetChanged();
                break;

            case "lista principal":
                MainActivity.adaptador.notifyDataSetChanged();
                break;

            case "lista tab desarrolladora":
                DesarrolladoraFragment.adaptador.notifyDataSetChanged();
                break;

            case "lista tab plataforma":
                PlataformaFragment.adaptador.notifyDataSetChanged();
                break;

            case "lista tab tienda":
                TiendaFragment.adaptador.notifyDataSetChanged();
                break;

            case "lista tab genero":
                GeneroFragment.adaptador.notifyDataSetChanged();
                break;
            case "lista tiendas mapa":
                break;
            default:
                Log.e("LLAMADA NO ENCONTRADA", "Se ha llamado al refresco del adaptador desde un lugar desconocido");
                break;
        }*/
    }
}
