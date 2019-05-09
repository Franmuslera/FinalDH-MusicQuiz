package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentQuiz extends Fragment {

    private static final String TRACK_GANADOR = "trackGanador";
    private static final String TRACKS_PERDEDORES = "tracksPerdedores";

    private LinearLayout lytOpciones;
    private Button btnOpcion1;
    private Button btnOpcion2;
    private Button btnOpcion3;
    private Button btnOpcion4;
    private Button btnGanador;

    private MediaPlayer mediaPlayer;
    private String urlTrackGanador;
    private int milisegs;
    private ProgressBar prgLoadingTrack;
    private ImageButton btnPlayTrack;
    private Button btnEscucharMas;
    private TextView txtPuntosEnJuego;

    private Integer puntosEnJuego;

    private ListenerOpcionSeleccionada listener;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mediaPlayer.stop();
            btnPlayTrack.setEnabled(true);
            btnEscucharMas.setEnabled(true);
            //Muestro el boton y las opciones despues de escuchar
            //Sirve para la primera vez
            if (milisegs < 10000) {
                btnEscucharMas.setVisibility(View.VISIBLE);
            }

            lytOpciones.setVisibility(View.VISIBLE);
        }
    };

    Runnable runnableNextTrack = new Runnable() {
        @Override
        public void run() {
            listener.avanzarViewPager(puntosEnJuego);
        }
    };

    public static FragmentQuiz getFragment(Track trackGanador, List<Track> tracksCandidatos){

        FragmentQuiz fragmentQuiz = new FragmentQuiz();
        Bundle bundle = new Bundle();
        List<Track> tracksRandom = new ArrayList<>();

        while (tracksRandom.size() < 3){

            int rndIndex = (int)(Math.random() * tracksCandidatos.size());
            Track track = tracksCandidatos.get(rndIndex);

            if (!tracksRandom.contains(track) && !track.equals(trackGanador)){
                tracksRandom.add(track);
            }
        }

        TrackContainer tracksPerdedores = new TrackContainer(tracksRandom);

        bundle.putSerializable(TRACK_GANADOR, trackGanador);
        bundle.putSerializable(TRACKS_PERDEDORES, tracksPerdedores);

        fragmentQuiz.setArguments(bundle);

        return fragmentQuiz;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        Bundle bundle = getArguments();

        Track trackGanador = (Track)bundle.getSerializable(TRACK_GANADOR);
        TrackContainer tracksPerdedores = (TrackContainer)bundle.getSerializable(TRACKS_PERDEDORES);

        //Inicializo los puntos
        puntosEnJuego = 10;
        urlTrackGanador = trackGanador.getPreview();

        prgLoadingTrack = view.findViewById(R.id.prgLoadingTrack);
        btnEscucharMas = view.findViewById(R.id.btnEscucharMas);
        btnPlayTrack = view.findViewById(R.id.btnPlayTrack);
        txtPuntosEnJuego = view.findViewById(R.id.txtPuntosEnJuego);
        milisegs = 1000;

        lytOpciones = view.findViewById(R.id.lytOpciones);
        btnOpcion1 = view.findViewById(R.id.btnOpcion1);
        btnOpcion2 = view.findViewById(R.id.btnOpcion2);
        btnOpcion3 = view.findViewById(R.id.btnOpcion3);
        btnOpcion4 = view.findViewById(R.id.btnOpcion4);

        //Preparo el media player
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {

            mediaPlayer.setDataSource(urlTrackGanador);

        } catch (IOException e){
            Toast.makeText(getActivity(), "Ocurrió un error al reproducir el audio", Toast.LENGTH_SHORT).show();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaPlayer = mp;
                mediaPlayer.start();
                handler.postDelayed(runnable, milisegs);
                prgLoadingTrack.setVisibility(View.GONE);
                btnPlayTrack.setVisibility(View.VISIBLE);
            }
        });

        btnPlayTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnPlayTrack.setEnabled(false);
                btnEscucharMas.setEnabled(false);
                btnPlayTrack.setVisibility(View.GONE);
                prgLoadingTrack.setVisibility(View.VISIBLE);
                mediaPlayer.prepareAsync();
            }
        });

        btnEscucharMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Oculto el boton para que escuche y no presione de nuevo
                btnEscucharMas.setVisibility(View.INVISIBLE);

                if (milisegs == 5000){
                    milisegs = milisegs + 5000;
                }else {
                    milisegs = milisegs + 2000;
                }

                switch (milisegs){
                    case 3000:
                        puntosEnJuego = 5;
                        break;
                    case 5000:
                        puntosEnJuego = 3;
                        break;
                    case 10000:
                        puntosEnJuego = 1;
                        break;
                }

                txtPuntosEnJuego.setText("SUMAS " + puntosEnJuego + " PUNTO");

                //Simulo el click del botón play
                btnPlayTrack.performClick();
            }
        });

        armarOpciones(trackGanador, tracksPerdedores.getData());

        return view;
    }

    public void armarOpciones(Track trackGanador, List<Track> tracksPerdedor) {

        int nroRandom = (int)(Math.random() * 4);

        //Seteo el ganador random
        switch (nroRandom){
            case 0:
                setTrackGanador(btnOpcion1, trackGanador);
                btnGanador = btnOpcion1;
                setTrackPerdedor(btnOpcion2, tracksPerdedor.get(0));
                setTrackPerdedor(btnOpcion3, tracksPerdedor.get(1));
                setTrackPerdedor(btnOpcion4, tracksPerdedor.get(2));
                break;
            case 1:
                setTrackGanador(btnOpcion2, trackGanador);
                btnGanador = btnOpcion2;
                setTrackPerdedor(btnOpcion1, tracksPerdedor.get(0));
                setTrackPerdedor(btnOpcion3, tracksPerdedor.get(1));
                setTrackPerdedor(btnOpcion4, tracksPerdedor.get(2));
                break;
            case 2:
                setTrackGanador(btnOpcion3, trackGanador);
                btnGanador = btnOpcion3;
                setTrackPerdedor(btnOpcion1, tracksPerdedor.get(0));
                setTrackPerdedor(btnOpcion2, tracksPerdedor.get(1));
                setTrackPerdedor(btnOpcion4, tracksPerdedor.get(2));
                break;
            case 3:
                setTrackGanador(btnOpcion4, trackGanador);
                btnGanador = btnOpcion4;
                setTrackPerdedor(btnOpcion1, tracksPerdedor.get(0));
                setTrackPerdedor(btnOpcion2, tracksPerdedor.get(1));
                setTrackPerdedor(btnOpcion3, tracksPerdedor.get(2));
                break;
        }

    }

    private void setTrackGanador(final Button btn, Track track){

        btn.setText(track.getNombre());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloquearBotones();
                btnPlayTrack.setEnabled(false);
                btn.setTextColor(getResources().getColor(R.color.colorText));
                btn.setBackground(getResources().getDrawable(R.drawable.button_winner));
                handler.postDelayed(runnableNextTrack, 1500);
            }
        });

    }

    private void setTrackPerdedor(final Button btn, Track track){

        btn.setText(track.getNombre());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloquearBotones();
                btnPlayTrack.setEnabled(false);
                btn.setTextColor(getResources().getColor(R.color.colorText));
                btn.setBackground(getResources().getDrawable(R.drawable.button_loser));
                btnGanador.setBackground(getResources().getDrawable(R.drawable.button_winner));
                btnGanador.setTextColor(getResources().getColor(R.color.colorText));
                puntosEnJuego = 0;
                handler.postDelayed(runnableNextTrack, 1500);
            }
        });

    }

    private void bloquearBotones(){
        btnOpcion1.setEnabled(false);
        btnOpcion2.setEnabled(false);
        btnOpcion3.setEnabled(false);
        btnOpcion4.setEnabled(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ListenerOpcionSeleccionada)context;
    }

    public interface ListenerOpcionSeleccionada{
        void avanzarViewPager(Integer ptsSumar);
    }

}
