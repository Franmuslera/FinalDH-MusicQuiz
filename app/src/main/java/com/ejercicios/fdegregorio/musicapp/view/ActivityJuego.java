package com.ejercicios.fdegregorio.musicapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.ArtistController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Quiz;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityJuego extends AppCompatActivity implements FragmentQuiz.ListenerOpcionSeleccionada{

    public static final String ARTISTA_ID = "artistaId";
    public static final String FRAGMENT_RESULT_QUIZ = "fragmentResultQuiz";
    private String sharePath="no";

    private QuizViewPager quizViewPager;
    private AdapterQuizViewPager adapterVP;
    private TextView txtPagina;
    private TextView txtPuntaje;
    private Integer ptsAcumulados;
    private Integer idArtista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Bundle bundle = getIntent().getExtras();
        idArtista = bundle.getInt(ARTISTA_ID);

        quizViewPager = findViewById(R.id.quizViewPager);
        adapterVP = new AdapterQuizViewPager(getSupportFragmentManager());

        quizViewPager.setPagingEnabled(false);
        quizViewPager.setAdapter(adapterVP);
        quizViewPager.setClipToPadding(false);

        quizViewPager.setCurrentItem(0);

        txtPagina = findViewById(R.id.txtPagina);
        txtPuntaje = findViewById(R.id.txtPuntaje);
        txtPagina.setText((quizViewPager.getCurrentItem() + 1) + "/10");
        ptsAcumulados = 0;

        cargarJuego(idArtista);
    }

    public void cargarJuego(final Integer idArtista) {

        ArtistController artistController = new ArtistController();

        artistController.getTopTracksByArtistId(idArtista, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> tracks) {

                int cantTracks = tracks.size();
                List<Track> tracksGanadores = new ArrayList<>();

                while (tracksGanadores.size() < 10){

                    int rndIndex = (int)(Math.random() * cantTracks);
                    Track track = tracks.get(rndIndex);

                    if (!tracksGanadores.contains(track)){
                        tracksGanadores.add(track);
                    }
                }

                List<FragmentQuiz> fragmentsQuiz = new ArrayList<>();

                for (int i = 0; i < tracksGanadores.size(); i++) {

                    fragmentsQuiz.add(FragmentQuiz.getFragment(tracksGanadores.get(i), tracks));
                }

                adapterVP.setLstFragmentQuiz(fragmentsQuiz);
            }

        });

    }

    @Override
    public void avanzarViewPager(Integer ptsSumar) {

        int posActual = quizViewPager.getCurrentItem();
        ptsAcumulados = ptsAcumulados + ptsSumar;
        txtPuntaje.setText(ptsAcumulados.toString() + " pts.");

        switch (posActual){
            case 9:
                guardarPuntaje();
                setFragment(FragmentResultQuiz.getFragment(idArtista, ptsAcumulados), FRAGMENT_RESULT_QUIZ);
                quizViewPager.setVisibility(View.GONE);
                break;
            default:
                quizViewPager.setCurrentItem(posActual + 1);
                break;
        }

        txtPagina.setText((quizViewPager.getCurrentItem() + 1) + "/10");

    }

    private void setFragment(Fragment fragment, String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contenedor_fragment_resultado, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void guardarPuntaje(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference puntosUsuario = db.getReference("quiz")
                                                .child(idArtista.toString())
                                                .child(user.getUid() + "_" + new Date().getTime());

        //Armo la fecha
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);

        puntosUsuario.setValue(new Quiz(ptsAcumulados, strDate, user.getDisplayName()));

    }


}
