package com.ejercicios.fdegregorio.musicapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.R;

public class AlbumsActivity extends AppCompatActivity implements FragmentAlbums.ListenerFragmentAlbums {

    private static final String FRAGMENT_LISTA_ALBUM = "fragmentLista";
    private static final String FRAGMENT_DETALLE_ALBUM = "fragmentDetalle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        //TODO: Reemplazar para que el primer fragment que pegue sea un FragmentBuscador
        setFragment(new FragmentAlbums(), FRAGMENT_LISTA_ALBUM);
    }

    private void setFragment(Fragment fragment, String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragments_albums, fragment);

        if (!tag.equals(FRAGMENT_LISTA_ALBUM)) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void informarAlbumSeleccionado(Album albumSeleccionado) {

        FragmentDetalleAlbum fragmentDetalleAlbum = new FragmentDetalleAlbum();
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentDetalleAlbum.CLAVE_ALBUM, albumSeleccionado.getId());

        fragmentDetalleAlbum.setArguments(bundle);

        setFragment(fragmentDetalleAlbum, FRAGMENT_DETALLE_ALBUM);
    }
}
