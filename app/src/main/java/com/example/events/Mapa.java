package com.example.events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapa;

    private float eventoLat;
    private float eventoLong;
    private String eventoLugar, eventoNom, procedencia;
    private ArrayList<Evento> arrayTiendas = new ArrayList<>();

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        this.setTitle("Evento en el mapa");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        // Inicializa el sistema de mapas de Google
        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    //Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

                    mapFragment.getMapAsync(Mapa.this);
                }
            }
        });


        Intent intent = getIntent();
        procedencia = intent.getStringExtra("procedencia");

        if(procedencia.equals("mapaEvento")){
            eventoLat = intent.getFloatExtra("eventoLat", 0);
            eventoLong = intent.getFloatExtra("eventoLong", 0);
            eventoNom = intent.getStringExtra("eventoNom");
            eventoLugar = intent.getStringExtra("eventoLug");

            DescargaDatos tarea = new DescargaDatos(this, arrayTiendas, "mapa");
            tarea.execute(Constantes.URL + "eventosNombre?nombre=" + eventoNom);
        }else{
            arrayTiendas = MainActivity.eventos;
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        mapa.getUiSettings().setMyLocationButtonEnabled(true);

        //CameraUpdate camara = CameraUpdateFactory.newLatLng(new LatLng(arrayTiendas.get(0).getLatitud(), arrayTiendas.get(0).getLongitud()));
        CameraUpdate camara = CameraUpdateFactory.newLatLng(latLng);

        // Coloca la vista del mapa sobre la posición del restaurante
        // y activa el zoom para verlo de cerca
        mapa.moveCamera(camara);
        mapa.animateCamera(CameraUpdateFactory.zoomTo(13.0f));

        if (procedencia.equals("mapaTotal"))
            /*DESCOMENTAR SI SE REQUIERE PINTAR MÄS DE UN PUNTO AL QUE IR*/
            for (Evento eventos : arrayTiendas) {
                // Añade una marca en la posición del restaurante con el nombre de éste
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(eventos.getLatitud(), eventos.getLongitud()))
                        .title(eventos.getNombre()));
            }
        else
            mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(eventoLat, eventoLong))
                    .title(eventoLugar));

        //MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!").icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.));
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Te encuentras aquí").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //markerOptions.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.m));
        mapa.addMarker(markerOptions);

        //Define list to get all latlng for the route
        List<LatLng> path = new ArrayList();


        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyD9flnxFwr66BZaKXT21YkdSt0Did4Vh-0")
                .build();

        //Log.d("DAVID LOC", latLng.latitude+","+latLng.longitude+"\t"+arrayTiendas.get(0).getLatitud()+","+ arrayTiendas.get(0).getLongitud());
        if(procedencia.equals("mapaEvento")){
            DirectionsApiRequest req = DirectionsApi.getDirections(context, latLng.latitude + "," + latLng.longitude, arrayTiendas.get(0).getLatitud() + "," + arrayTiendas.get(0).getLongitud());
            try {
                DirectionsResult res = req.await();

                //Loop through legs and steps to get encoded polylines of each step
                if (res.routes != null && res.routes.length > 0) {
                    DirectionsRoute route = res.routes[0];

                    if (route.legs != null) {
                        for (int i = 0; i < route.legs.length; i++) {
                            DirectionsLeg leg = route.legs[i];
                            if (leg.steps != null) {
                                for (int j = 0; j < leg.steps.length; j++) {
                                    DirectionsStep step = leg.steps[j];
                                    if (step.steps != null && step.steps.length > 0) {
                                        for (int k = 0; k < step.steps.length; k++) {
                                            DirectionsStep step1 = step.steps[k];
                                            EncodedPolyline points1 = step1.polyline;
                                            if (points1 != null) {
                                                //Decode polyline and add points to list of route coordinates
                                                List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                                for (com.google.maps.model.LatLng coord1 : coords1) {
                                                    path.add(new LatLng(coord1.lat, coord1.lng));
                                                }
                                            }
                                        }
                                    } else {
                                        EncodedPolyline points = step.polyline;
                                        if (points != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords = points.decodePath();
                                            for (com.google.maps.model.LatLng coord : coords) {
                                                path.add(new LatLng(coord.lat, coord.lng));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Log.e("VALERY MAPA", ex.getLocalizedMessage());
            }
            //Draw the polyline
            if (path.size() > 0) {
                PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
                mapa.addPolyline(opts);
            }
        }





    }
/* //Se utiliza para poder poner imágenes de forma más eficiente
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.marker_icon);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    */

    @Override
    protected void onResume() {
        // Obtiene una vista de cámara
        super.onResume();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }
}
