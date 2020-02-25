package com.example.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ComentarioAdapter extends BaseAdapter {
    private Context contexto;
    private ArrayList<Comentario> comentarios;
    private LayoutInflater layoutinflater;

    ComentarioAdapter(Context contexto, ArrayList<Comentario> comentarios){
        this.contexto = contexto;
        this.comentarios = comentarios;
        layoutinflater = LayoutInflater.from(contexto);
    }

    static class ViewHolder{
        TextView usuario;
        TextView contenido;
        TextView valoracion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;

        if(convertView == null){
            convertView = layoutinflater.inflate(R.layout.item_comentario, null);
            viewholder = new ViewHolder();
            viewholder.usuario = convertView.findViewById(R.id.txvNomEvento);
            viewholder.contenido = convertView.findViewById(R.id.txvDescripcionEvento);
            viewholder.valoracion = convertView.findViewById(R.id.txvValoracionEvento);
            convertView.setTag(viewholder);
        }else
            viewholder = (ViewHolder) convertView.getTag();

        Comentario comentario = comentarios.get(position);
        viewholder.usuario.setText(comentario.getUsuario());
        viewholder.contenido.setText(comentario.getContenido());
        viewholder.valoracion.setText(String.valueOf(comentario.getValoracion()));

        return convertView;
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
