package com.ejercicios.fdegregorio.musicapp.view;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.AlbumController;
import com.ejercicios.fdegregorio.musicapp.controller.ArtistController;
import com.ejercicios.fdegregorio.musicapp.controller.ConnectivityController;
import com.ejercicios.fdegregorio.musicapp.controller.TrackController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.ArtistContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerAlbumClick;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerTrackClick;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FragmentDetalleArtista extends Fragment implements AdapterAlbums.ListenerAdapterItems,AdapterCanciones.ListenerAdapterCanciones {

    public static final String CLAVE_ARTISTA= "Artista";
    private ImageView imageViewArtista;
    private TextView textViewNombreArtista;
    private TextView textViewFans;

    private Button buttonJugar;
    private RecyclerView recyclerViewCanciones;
    private RecyclerView recyclerViewAlbums;
    private AdapterCanciones adapterCanciones;
    private AdapterAlbums adapterAlbums;

    private Integer idArtista;
    private ListenerTrackClick listenerTrackClick;
    private ListenerAlbumClick listenerAlbumClick;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_artista, container, false);

        imageViewArtista = view.findViewById(R.id.imagenArtistaDetalle);
        textViewNombreArtista = view.findViewById(R.id.nombreDetalleArtista);
        textViewFans = view.findViewById(R.id.txtViewFansArtista);

        buttonJugar = view.findViewById(R.id.btnJugar);
        recyclerViewCanciones = view.findViewById(R.id.recyclerCancionesDetalle);
        this.adapterCanciones = new AdapterCanciones(this);
        recyclerViewAlbums = view.findViewById(R.id.recyclerAlbumsDetalle);
        this.adapterAlbums = new AdapterAlbums(this);

        final LinearLayout lytOfflineMode = getActivity().findViewById(R.id.lytOfflineMode);


        Bundle bundle = getArguments();
        idArtista = bundle.getInt(CLAVE_ARTISTA);
        buscarArtistaById(idArtista);
        cargar(idArtista);

        buttonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FirebaseAuth.getInstance().getCurrentUser() != null){

                    if (ConnectivityController.isConnected(getContext())) {
                        iniciarJuego();
                        lytOfflineMode.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(getContext(), "No es posible jugar en el Modo Offline", Toast.LENGTH_SHORT).show();
                        lytOfflineMode.setVisibility(View.VISIBLE);
                    }

                }else{
                    mostrarDialog();
                }
            }
        });

        return view;
    }



    public void buscarArtistaById(Integer idArtist){


        ArtistController artistController = new ArtistController();
        artistController.getArtistById(idArtist, new ResultListener<Artist>() {
            @Override
            public void finish(Artist artist) {

                Picasso.get()
                        .load(artist.getImagen())
                        .placeholder(R.drawable.music_placeholder)
                        .into(imageViewArtista);
                textViewNombreArtista.setText(artist.getNombre());

                if (artist.getFans() == null) {
                    textViewFans.setText("info no disponible");
                }else {
                    textViewFans.setText(artist.getFans() +" fans");
                }


            }
        }, getContext());
    }

    public void cargar (Integer idArtist) {

        ArtistController artistControllerTrack = new ArtistController();

        artistControllerTrack.getTracksArtistById(idArtist, new ResultListener<List<Track>>() {
             @Override
             public void finish(List<Track> result) {
                 LinearLayoutManager managerAlbums = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                 recyclerViewCanciones.setLayoutManager(managerAlbums);
                 recyclerViewCanciones.setAdapter(adapterCanciones);
                 adapterCanciones.setResult(result);
            }
         }, getContext());

        ArtistController artistControllerAlbum = new ArtistController();
        artistControllerAlbum.getAlbumArtistById(idArtist, new ResultListener<List<Album>>() {
             @Override
             public void finish(List<Album> result) {
                 LinearLayoutManager managerAlbums = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                 recyclerViewAlbums.setLayoutManager(managerAlbums);
                 recyclerViewAlbums.setAdapter(adapterAlbums);
                 adapterAlbums.setResult(result);
            }
         }, getContext());
    }


   @Override
   public void albumSeleccionado(Album albumSeleccionado) {
       listenerAlbumClick.albumClick(albumSeleccionado);

   }

   public void iniciarJuego(){

        Intent intent = new Intent(getActivity(), ActivityJuego.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ActivityJuego.ARTISTA_ID, idArtista);
        intent.putExtras(bundle);
        startActivity(intent);
   }

   public void mostrarDialog(){

       AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialogTheme)
                .setTitle("Registrate!")
                .setMessage("Para poder jugar, tenés que estar registrado.")
                .setPositiveButton("Ir al login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                })
                .setCancelable(false)
                .create();

       dialog.setCanceledOnTouchOutside(false);
       dialog.show();
   }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerTrackClick = (ListenerTrackClick) context;
        listenerAlbumClick = (ListenerAlbumClick) context;
    }

    @Override
    public void reproducirCancion(Track cancionSeleccionada) {
        listenerTrackClick.trackClick(cancionSeleccionada);
    }

    public interface ListenerFragmentArtista{
        public void informarAlbumSeleccionado(Album albumSeleccionado);
    }
}
