package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.ConnectivityController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Genero;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerArtistClick;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGeneros extends Fragment implements AdapterArtistas.ListenerAdapterArtistas, AdapterGeneros.ListenerAdapterGeneros {

    private ListenerArtistClick listenerArtistClick;
    private AdapterGeneros adapterGeneros;
    private AdapterArtistas adapterArtistas;
    RecyclerView recyclerViewGeneros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_generos, container, false);

        recyclerViewGeneros = view.findViewById(R.id.recyclerGeneros);
        LinearLayoutManager layoutManagerGenero = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewGeneros.setLayoutManager(layoutManagerGenero);

        this.adapterGeneros = new AdapterGeneros(this, this);
        recyclerViewGeneros.setAdapter(adapterGeneros);

        LinearLayout lytOfflineMode = getActivity().findViewById(R.id.lytOfflineMode);

        //Muestro el mensaje de Modo Offline si no hay conexión
        if (ConnectivityController.isConnected(getContext())){
            lytOfflineMode.setVisibility(View.GONE);
        }else {
            lytOfflineMode.setVisibility(View.VISIBLE);
        }

        //Cargo los generos cuando se crea el Fragment
        adapterGeneros.setGeneros(cargarGeneros());

        return view;
    }

    public List<Genero> cargarGeneros(){

        List<Genero> generos = new ArrayList<>();

        generos.add(new Genero(132, "Pop"));
        generos.add(new Genero(152, "Rock"));
        generos.add(new Genero(169, "Soul & Funk"));
        generos.add(new Genero(113, "Dance"));
        generos.add(new Genero(165, "R&B"));
        generos.add(new Genero(85, "Alternativo"));
        generos.add(new Genero(466, "Folk"));
        generos.add(new Genero(129, "Jazz"));

        return generos;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerArtistClick = (ListenerArtistClick) context;
    }

    @Override
    public void generoSeleccionado(Genero generoSeleccionado) {

    }

    @Override
    public void artistaSeleccionado(Artist artistaSeleccionado) {
        listenerArtistClick.artistClick(artistaSeleccionado);

    }

  /*
    public interface ListenerFragmentGeneros {


        public void informarArtistaSeleccionado(Artist artistaSeleccionado);
    } */

}
