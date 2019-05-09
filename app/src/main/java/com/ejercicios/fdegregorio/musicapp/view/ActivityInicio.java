package com.ejercicios.fdegregorio.musicapp.view;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.ConnectivityController;
import com.ejercicios.fdegregorio.musicapp.controller.MediaPlayerController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerAlbumClick;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerArtistClick;
import com.ejercicios.fdegregorio.musicapp.utils.ListenerTrackClick;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class ActivityInicio extends AppCompatActivity implements    ListenerArtistClick,
                                                                    ListenerAlbumClick,
                                                                    ListenerTrackClick,
                                                                    GoogleApiClient.OnConnectionFailedListener,
                                                                    FragmentCuenta.ListenerFragmentCuenta
                                                                    {

    private static final String FRAGMENT_LISTA_GENEROS = "fragmentGeneros";
    private static final String FRAGMENT_ARTISTA = "fragmentArtista";
    private static final String FRAGMENT_SEARCH = "fragmentSearch";
    private static final String FRAGMENT_CUENTA = "fragmentCuenta";
    private static final String FRAGMENT_ALBUM = "fragmentAlbum";
    private static final String FRAGMENT_RESULT_QUIZ = "fragmentResultQuiz";

    private BottomNavigationView bottomNavigationView;

    private LinearLayout lytReproductor;
    private ImageView btnPlay;
    private ImageView btnPausa;
    private ImageView btnStop;
    private TextView txtNombreCancion;
    private TextView txtNombreArtista;

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(this.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        pegarFragment(new FragmentGeneros(), FRAGMENT_LISTA_GENEROS);

        bottomNavigationView = findViewById(R.id.nav_bottom_inicio);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Menu menuBottom = bottomNavigationView.getMenu();

        if (user == null){
            //Oculto la opcion Mi cuenta del nav bottom
            menuBottom.findItem(R.id.op_account).setVisible(false);

        }else {
            menuBottom.findItem(R.id.op_account).setVisible(true);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.op_home:
                        pegarFragment(new FragmentGeneros(), FRAGMENT_LISTA_GENEROS);
                        break;
                    case R.id.op_search:
                        pegarFragment(new FragmentSearch(),FRAGMENT_SEARCH);
                        break;
                    case R.id.op_account:
                        pegarFragment(new FragmentCuenta(),FRAGMENT_CUENTA);
                        break;
                }

                return true;
            }
        });

        //Busco los controladores del reproductor y defino los eventos click
        lytReproductor = findViewById(R.id.reproductor);
        btnPausa = findViewById(R.id.btnPause);
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);
        txtNombreCancion = findViewById(R.id.txtNombreCancion);
        txtNombreArtista = findViewById(R.id.txtNombreArtista);


        //PAUSA
        btnPausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerController.pauseTrack();
                btnPausa.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
            }
        });

        //PLAY
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerController.resumeTrack();
                btnPlay.setVisibility(View.GONE);
                btnPausa.setVisibility(View.VISIBLE);
            }
        });

        //STOP
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayerController.stopTrack();
                lytReproductor.setVisibility(View.GONE);
            }
        });

    }

    private void goToLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void logOut(){

        //Cierro sesion en Firebase
        firebaseAuth.signOut();

        //Revoco sesion en Google
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

                if (status.isSuccess()){

                    goToLogin();

                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar la sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void informarLogout() {
        logOut();
    }

    private void pegarFragment(Fragment fragment, String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.contenedor_fragments_inicio, fragment, tag);

        if (!tag.equals(FRAGMENT_LISTA_GENEROS)) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void trackClick(Track track) {

        if (ConnectivityController.isConnected(this)) {
            try {

                //En cada click a una cancion, freno el mediaplayer
                MediaPlayerController.stopTrack();

                //Seteo el listener para cuando termine de reproducir
                MediaPlayerController.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        MediaPlayerController.stopTrack();
                        lytReproductor.setVisibility(View.GONE);
                    }
                });

                MediaPlayerController.playTrack(track.getPreview());
                lytReproductor.setVisibility(View.VISIBLE);
                btnPlay.setVisibility(View.GONE);
                btnPausa.setVisibility(View.VISIBLE);
                txtNombreCancion.setText(track.getNombre());
                txtNombreArtista.setText(track.getArtist().getNombre());

            } catch (IOException e) {
                Toast.makeText(this, "Error al reproducir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "No se pueden reproducir canciones en el modo offline", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void artistClick(Artist artist) {
        FragmentDetalleArtista fragmentDetalleArtista = new FragmentDetalleArtista();
        Bundle unBundle = new Bundle();
        unBundle.putInt(FragmentDetalleArtista.CLAVE_ARTISTA,artist.getId());
        fragmentDetalleArtista.setArguments(unBundle);
        pegarFragment(fragmentDetalleArtista,FRAGMENT_ARTISTA);
    }

    @Override
    public void albumClick(Album album) {
        FragmentDetalleAlbum fragmentDetalleAlbum = new FragmentDetalleAlbum();
        Bundle bundle = new Bundle();
        bundle.putInt(FragmentDetalleAlbum.CLAVE_ALBUM,album.getId());
        fragmentDetalleAlbum.setArguments(bundle);
        pegarFragment(fragmentDetalleAlbum,FRAGMENT_ALBUM);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentGeneros fragmentGeneros = (FragmentGeneros)fragmentManager.findFragmentByTag(FRAGMENT_LISTA_GENEROS);
        FragmentSearch fragmentSearch = (FragmentSearch)fragmentManager.findFragmentByTag(FRAGMENT_SEARCH);
        FragmentCuenta fragmentCuenta = (FragmentCuenta)fragmentManager.findFragmentByTag(FRAGMENT_CUENTA);
        FragmentResultQuiz fragmentResultQuiz = (FragmentResultQuiz)fragmentManager.findFragmentByTag(FRAGMENT_RESULT_QUIZ);

        if (fragmentGeneros != null && fragmentGeneros.isVisible()) {
            finishAndRemoveTask();
        }else {

            //Sólo vuelvo al fragment inicio cuando estoy en el search o en la cuenta
            if ((fragmentSearch != null && fragmentSearch.isVisible()) ||
                (fragmentCuenta != null && fragmentCuenta.isVisible()) ||
                (fragmentResultQuiz != null && fragmentResultQuiz.isVisible())) {

                pegarFragment(new FragmentGeneros(), FRAGMENT_LISTA_GENEROS);
                bottomNavigationView.setSelectedItemId(R.id.op_home);
            }else {
                super.onBackPressed();
            }
        }

    }


}
