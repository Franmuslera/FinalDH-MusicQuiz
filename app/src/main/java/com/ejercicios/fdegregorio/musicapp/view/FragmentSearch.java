package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.AlbumController;
import com.ejercicios.fdegregorio.musicapp.controller.ArtistController;
import com.ejercicios.fdegregorio.musicapp.controller.ConnectivityController;
import com.ejercicios.fdegregorio.musicapp.controller.TrackController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerAlbumClick;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerArtistClick;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerTrackClick;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

public class FragmentSearch extends Fragment implements AdapterSearchCanciones.ListenerAdapterSearchCanciones,AdapterSearchArtistas.ListenerAdapterSearchArtistas,AdapterSearchAlbums.ListenerAdapterSearchAlbum {

    private RecyclerView recyclerViewCanciones;
    private AdapterSearchCanciones adapterSearchCanciones;
    private RecyclerView recyclerViewAlbums;
    private AdapterSearchAlbums adapterSearchAlbums;
    private RecyclerView recyclerViewArtistas;
    private AdapterSearchArtistas adapterSearchArtistas;

    private LinearLayout contCanciones;
    private LinearLayout contAlbums;
    private LinearLayout contArtistas;
    private EditText edtSearch;
    private ProgressBar prgSearch;

    private ListenerTrackClick listenerTrackClick;
    private ListenerArtistClick listenerArtistClick;
    private ListenerAlbumClick listenerAlbumClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edtSearch = view.findViewById(R.id.edtSearch);

        //Muestro el mensaje de Modo Offline si no hay conexión
        if (ConnectivityController.isConnected(getContext())){
            getActivity().findViewById(R.id.lytOfflineMode).setVisibility(View.GONE);
            edtSearch.setEnabled(true);
        }else {
            getActivity().findViewById(R.id.lytOfflineMode).setVisibility(View.VISIBLE);
            edtSearch.setEnabled(false);
        }

        contCanciones = view.findViewById(R.id.contCanciones);
        contAlbums = view.findViewById(R.id.contAlbums);
        contArtistas = view.findViewById(R.id.contArtistas);
        prgSearch = view.findViewById(R.id.prgSearch);

        recyclerViewCanciones = view.findViewById(R.id.recyclerSearchCanciones);
        this.adapterSearchCanciones = new AdapterSearchCanciones(this);

        recyclerViewAlbums = view.findViewById(R.id.recyclerSearchAlbums);
        this.adapterSearchAlbums = new AdapterSearchAlbums(this);

        recyclerViewArtistas = view.findViewById(R.id.recyclerSearchArtistas);
        this.adapterSearchArtistas = new AdapterSearchArtistas(this);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    buscar(edtSearch.getText().toString());
                    closeKeyboard();
                    return true;
                }

                return false;
            }


        });




        return view;
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void buscar(String query){

        //Cuando inicia la busqueda muestro el progress bar y oculto los contenedores
        contArtistas.setVisibility(View.INVISIBLE);
        contAlbums.setVisibility(View.INVISIBLE);
        contCanciones.setVisibility(View.INVISIBLE);
        prgSearch.setVisibility(View.VISIBLE);

        //Buscar Artistas
        ArtistController artistController = new ArtistController();

        artistController.getArtistByQuery(query, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> result) {

                //Cuando vuelve de buscar el artista oculto el progressBar
                prgSearch.setVisibility(View.GONE);

                if (!result.isEmpty()){

                    //Recycler Artistas
                    LinearLayoutManager managerArtists = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewArtistas.setLayoutManager(managerArtists);
                    recyclerViewArtistas.setAdapter(adapterSearchArtistas);
                    adapterSearchArtistas.setResult(result);

                    contArtistas.setVisibility(View.VISIBLE);

                }

            }
        });

        //Buscar albums
        AlbumController albumController = new AlbumController();

        albumController.getAlbumsByQuery(query, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result) {

                if (!result.isEmpty()){

                    //Recycler Albums
                    LinearLayoutManager managerAlbums = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewAlbums.setLayoutManager(managerAlbums);
                    recyclerViewAlbums.setAdapter(adapterSearchAlbums);
                    adapterSearchAlbums.setResult(result);

                    contAlbums.setVisibility(View.VISIBLE);

                }

            }
        });

        //Buscar canciones
        TrackController trackController = new TrackController();

        trackController.getTracksSearch(query, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {

                if (!result.isEmpty()){

                    //Recycler Canciones
                    LinearLayoutManager managerCanciones = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewCanciones.setLayoutManager(managerCanciones);
                    recyclerViewCanciones.setAdapter(adapterSearchCanciones);
                    adapterSearchCanciones.setResult(result);

                    contCanciones.setVisibility(View.VISIBLE);

                }

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listenerTrackClick = (ListenerTrackClick)context;
        listenerArtistClick = (ListenerArtistClick) context;
        listenerAlbumClick = (ListenerAlbumClick) context;
    }

    @Override
    public void reproducirCancion(Track cancionSeleccionada) {

        listenerTrackClick.trackClick(cancionSeleccionada);
    }

    @Override
    public void artistaSeleccionado(Artist artistaSeleccionado) {
        listenerArtistClick.artistClick(artistaSeleccionado);
    }

    @Override
    public void albumSeleccionado(Album album) {
        listenerAlbumClick.albumClick(album);
    }
}
