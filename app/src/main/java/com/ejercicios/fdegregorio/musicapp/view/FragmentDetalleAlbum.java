package com.ejercicios.fdegregorio.musicapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.controller.AlbumController;
import com.ejercicios.fdegregorio.musicapp.controller.ConnectivityController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerTrackClick;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentDetalleAlbum extends Fragment implements AdapterCancionesAlbum.ListenerAdapterCancionesAlbum{

    public static final String CLAVE_ALBUM = "Album";
    private ImageView imageViewAlbum;
    private TextView textViewNombreAlbum;
    private RecyclerView recyclerViewCanciones;
    private Button buttonAleatorio;
    private AdapterCancionesAlbum adapterCancionesAlbum;
    private ListenerTrackClick listenerTrackClick;
    private List<Track> listaCancionesAlbum;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle_album, container, false);



        imageViewAlbum = view.findViewById(R.id.imagenAlbumDetalle);
        textViewNombreAlbum = view.findViewById(R.id.nombreDetalleAlbum);
        recyclerViewCanciones = view.findViewById(R.id.recyclerCancionesAlbumDetalle);
        buttonAleatorio = view.findViewById(R.id.btnAleatorio);

        this.adapterCancionesAlbum= new AdapterCancionesAlbum(this);

        Bundle bundle = getArguments();
        Integer idAlbum = bundle.getInt(CLAVE_ALBUM);
        buscarAlbumById(idAlbum);
        cargarCanciones(idAlbum);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        buttonAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectivityController.isConnected(getContext())) {
                    listenerTrackClick.trackClick(getRamdomTrack());
                }else {
                    Toast.makeText(getContext(), "No se pueden reproducir canciones en el modo offline", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public void buscarAlbumById(Integer idAlbum){

        AlbumController albumController = new AlbumController();
        albumController.getAlbumById(idAlbum, new ResultListener<Album>() {
            @Override
            public void finish(Album album) {
                Picasso.get()
                        .load(album.getImagen())
                        .placeholder(R.drawable.music_placeholder)
                        .into(imageViewAlbum);
                textViewNombreAlbum.setText(album.getNombre());
            }
        }, getContext());



    }
    public void cargarCanciones(Integer idAlbum){
        AlbumController albumController = new AlbumController();
        albumController.getTracksAlbumById(idAlbum, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerViewCanciones.setLayoutManager(manager);
                recyclerViewCanciones.setAdapter(adapterCancionesAlbum);
                adapterCancionesAlbum.setResult(result);
                listaCancionesAlbum = result;


            }
        }, getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerTrackClick = (ListenerTrackClick) context;
    }

    @Override
    public void reproducirTrack(Track cancionSeleccionada) {
        listenerTrackClick.trackClick(cancionSeleccionada);
    }

    public Track getRamdomTrack(){

        int nroRandom = (int) (Math.random() * listaCancionesAlbum.size());

        return listaCancionesAlbum.get(nroRandom);

    }
}
