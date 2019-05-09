package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.controller.AlbumController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAlbums extends Fragment implements AdapterAlbums.ListenerAdapterItems {
    public static final String CLAVE_FRAGMENT_ALBUM = "FragmentAlbum";
    private ListenerFragmentAlbums listenerFragmentAlbums;
    private AdapterAlbums adapterAlbums;

    private void cargarAlbums(String query){

        AlbumController albumController = new AlbumController();
        albumController.getAlbumsByQuery(query, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result) {

                adapterAlbums.setAlbums(result);
                adapterAlbums.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerAlbums);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //this.adapterAlbums = new AdapterAlbums(this);
       // recyclerView.setAdapter(adapterAlbums);

        //TODO: Pasarle el valor del EditText del buscador en el onclick del boton Buscar
       // cargarAlbums("beatles");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerFragmentAlbums = (ListenerFragmentAlbums)context;
    }

    @Override
    public void albumSeleccionado(Album albumSeleccionado) {
        listenerFragmentAlbums.informarAlbumSeleccionado(albumSeleccionado);
    }

    public interface ListenerFragmentAlbums {

        public void informarAlbumSeleccionado(Album albumSeleccionado);
    }
}
