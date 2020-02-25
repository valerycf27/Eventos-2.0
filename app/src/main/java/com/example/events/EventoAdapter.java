package com.example.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventoAdapter extends BaseAdapter {
    private Context contexto;
    private ArrayList<Evento> eventos;
    private LayoutInflater layoutinflater;

    EventoAdapter(Context contexto, ArrayList<Evento> eventos){
        this.contexto = contexto;
        this.eventos = eventos;
        layoutinflater = LayoutInflater.from(contexto);
    }

    static class ViewHolder{
        ImageView imagen;
        TextView nombre;
        TextView lugar;
        TextView dia;
        TextView hora;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;

        if(convertView == null){
            convertView = layoutinflater.inflate(R.layout.item_evento, null);
            viewholder = new ViewHolder();
            //viewholder.imagen = convertView.findViewById(R.id.imgEvento);
            viewholder.nombre = convertView.findViewById(R.id.tvNombEvento);
            viewholder.lugar = convertView.findViewById(R.id.tvLugarMos);
            viewholder.dia = convertView.findViewById(R.id.tvDiaMos);
            viewholder.hora = convertView.findViewById(R.id.tvHoraMos);
            convertView.setTag(viewholder);
        }else
            viewholder = (ViewHolder) convertView.getTag();

        Evento evento = eventos.get(position);
        //viewholder.imagen.setImageBitmap(evento.getImagen());
        viewholder.nombre.setText(evento.getNombre());
        viewholder.lugar.setText(evento.getLugar());
        viewholder.dia.setText(evento.getFecha());
        viewholder.hora.setText(evento.getHora());

        return convertView;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
