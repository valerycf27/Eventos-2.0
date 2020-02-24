package com.example.events;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class OrganizadorFragment extends Fragment {

    private ArrayList<Evento> eventos = new ArrayList<>();
    public static EventoAdapter adaptador;
    private static String organizador;

    public OrganizadorFragment(String lugar) {
        this.organizador = lugar;
    }

    public static OrganizadorFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        OrganizadorFragment fragment = new OrganizadorFragment(organizador);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recogida de parámetros
        int position = getArguments().getInt("position");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organizador, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lvLugar = getView().findViewById(R.id.lvOrganizador);

        adaptador= new EventoAdapter(getActivity(), eventos);
        lvLugar.setAdapter(adaptador);
        registerForContextMenu(lvLugar);
    }

    @Override
    public void onResume() {
        super.onResume();

        eventos.clear();
        adaptador.notifyDataSetChanged();
        DescargaDatos tarea = new DescargaDatos(getActivity(), eventos, "Lista Organizador");

        tarea.execute(Constantes.URL+"eventosOrganizador?organizador="+ organizador);
    }
}
