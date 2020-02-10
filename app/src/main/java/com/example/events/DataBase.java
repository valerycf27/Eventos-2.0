package com.example.events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.events.Constantes.AFORO;
import static com.example.events.Constantes.ARTISTAS_INVITADOS;
import static com.example.events.Constantes.BBDD;
import static com.example.events.Constantes.DESCRIPCION;
import static com.example.events.Constantes.ESTRELLAS;
import static com.example.events.Constantes.FECHA;
import static com.example.events.Constantes.GUARDADO;
import static com.example.events.Constantes.HORA;
import static com.example.events.Constantes.IMAGEN;
import static com.example.events.Constantes.LUGAR;
import static com.example.events.Constantes.NOMBRE;
import static com.example.events.Constantes.ORGANIZADOR;
import static com.example.events.Constantes.PRECIO;
import static com.example.events.Constantes.TABLA_EVENTOS;

public class DataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private final String[] SELECT = new String[] {_ID, NOMBRE, LUGAR, FECHA, HORA, AFORO, ORGANIZADOR,ARTISTAS_INVITADOS, DESCRIPCION, PRECIO, ESTRELLAS, GUARDADO, IMAGEN};

    public DataBase(Context context) {
        super(context, BBDD, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_EVENTOS + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE + " TEXT,  " + LUGAR + " TEXT," +
                FECHA+" TEXT, "+HORA+" TEXT, "+ AFORO + " INTEGER, "+ ORGANIZADOR +" TEXT, "+ ARTISTAS_INVITADOS +" TEXT, "+ DESCRIPCION + " TEXT, "+PRECIO+" REAL, "+
                ESTRELLAS+ " REAL, "+GUARDADO+" INTEGER, "+ IMAGEN +" BLOB)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void nuevoEvento(Evento evento){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NOMBRE, evento.getNombre());
        values.put(LUGAR, evento.getLugar());
        values.put(FECHA, evento.getFecha());
        values.put(HORA, evento.getHora());
        values.put(AFORO, evento.getAforo());
        values.put(ORGANIZADOR, evento.getOrganizador());
        values.put(ARTISTAS_INVITADOS, evento.getArtistasInvitados());
        values.put(DESCRIPCION, evento.getDescripcion());
        values.put(PRECIO, evento.getPrecio());
        values.put(ESTRELLAS, evento.getEstrellas());
        values.put(GUARDADO, evento.isGuardado());
        values.put(IMAGEN, Util.getBytes(evento.getImagen()));
        db.insertOrThrow(TABLA_EVENTOS, null, values);
        db.close();
    }

    public ArrayList<Evento> getLista(Cursor cursor){
        ArrayList<Evento> eventos = new ArrayList<>();

        while(cursor.moveToNext()){
            Evento evento = new Evento(cursor.getFloat(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3),  cursor.getString(4), cursor.getInt(5),cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getFloat(9), cursor.getFloat(10),
                    cursor.getInt(11)>=1);

            evento.setImagen(Util.getBitmap((cursor.getBlob(12))));
            eventos.add(evento);
        }

        return eventos;
    }

    public ArrayList<Evento> getEventos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLA_EVENTOS, SELECT, null, null, null, null, NOMBRE);
        return getLista(cursor);
    }

    public ArrayList<Evento> getGuardados(){
        SQLiteDatabase db = getReadableDatabase();
        String[] guardados = new String[]{String.valueOf(1)};
        Cursor cursor =  db.query(TABLA_EVENTOS, SELECT, "guardado = ? ", guardados, null, null, NOMBRE);
        return getLista(cursor);
    }

    public void guardarEvento(Evento evento) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, evento.getNombre());
        values.put(GUARDADO, evento.isGuardado());
        String[] argumentos = new String[]{String.valueOf(evento.getID())};
        db.update(TABLA_EVENTOS, values, "_id = ?", argumentos);
        db.close();
    }

    public void descartarEvento(Evento evento){
        SQLiteDatabase db = getWritableDatabase();

        String[] argumentos = new String[]{String.valueOf(evento.getID())};
        db.delete(TABLA_EVENTOS, "_id = ?", argumentos);
        db.close();

    }
}
