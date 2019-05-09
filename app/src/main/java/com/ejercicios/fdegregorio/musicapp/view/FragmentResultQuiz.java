package com.ejercicios.fdegregorio.musicapp.view;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.R;
import com.ejercicios.fdegregorio.musicapp.controller.ArtistController;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Quiz;

import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;



public class FragmentResultQuiz extends Fragment {

    private Button buttonCompartir;

    private View viewACompartir;
    private ImageView imageViewArtist;
    private RecyclerView recyclerViewRanking;
    private TextView puntajeFinal;
    private TextView nombreArtista;






    private static final String ID_ARTISTA = "idArtista";
    private static final String PUNTAJE_USUARIO = "puntajeUsuario";
    private AdapterQuizRanking adapterQuizRanking;

    public static FragmentResultQuiz getFragment(Integer idArtista, Integer puntaje){

        FragmentResultQuiz fragmentResultQuiz = new FragmentResultQuiz();
        Bundle bundle = new Bundle();

        bundle.putInt(ID_ARTISTA, idArtista);
        bundle.putInt(PUNTAJE_USUARIO, puntaje);

        fragmentResultQuiz.setArguments(bundle);

        return fragmentResultQuiz;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_quiz, container, false);

        imageViewArtist=view.findViewById(R.id.imgArtistaResultQuiz);
        nombreArtista=view.findViewById(R.id.nombreResultadoQuizArtista);
        puntajeFinal=view.findViewById(R.id.puntajeFinal);
        viewACompartir=view.findViewById(R.id.viewACompartir);
        buttonCompartir= view.findViewById(R.id.btnCompartir);
        recyclerViewRanking=view.findViewById(R.id.recyclerRanking);

        this.adapterQuizRanking = new AdapterQuizRanking();






        buttonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guardarScreenshotFirebaseYCompartir(takeScreenshot(viewACompartir));
            }
        });

        Bundle bundle = getArguments();
        Integer idArtista = bundle.getInt(ID_ARTISTA);
        Integer puntajeUsuario = bundle.getInt(PUNTAJE_USUARIO);
        puntajeFinal.setText(puntajeUsuario.toString()+" puntos");
        buscarArtistaById(idArtista);



        mostrarRanking(idArtista);

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
                        .into(imageViewArtist);
                nombreArtista.setText(artist.getNombre());
            }
        }, getContext());
    }

    private void guardarScreenshotFirebaseYCompartir(byte[] baos){

        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        StorageReference usrReference = storage.getReference().child(user.getUid());
        final StorageReference imgUsrReference = usrReference.child(new Date().getTime() + ".jpg");
        UploadTask upload = imgUsrReference.putBytes(baos);

        upload.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Falló al capturar la pantalla", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgUsrReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Intent intentParaCompartir = new Intent(Intent.ACTION_SEND);
                        intentParaCompartir.setType("text/plain");
                        intentParaCompartir.putExtra(Intent.EXTRA_SUBJECT, "Comparti desde mi app");
                        intentParaCompartir.putExtra(Intent.EXTRA_TEXT, uri.toString());
                        startActivity(Intent.createChooser(intentParaCompartir, "Comparti!"));
                    }
                });
            }
        });

    }




    private void mostrarRanking(Integer idArtista){


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference artistQuiz = db.getReference().child("quiz")
                                                        .child(idArtista.toString());

        artistQuiz.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Quiz> ranking = new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Quiz quiz = ds.getValue(Quiz.class);
                    ranking.add(quiz);
                }

                Collections.sort(ranking, new Comparator<Quiz>() {
                    @Override
                    public int compare(Quiz o1, Quiz o2) {
                        return (o1.getPuntaje() < o2.getPuntaje()) ? 1 : (o1.getPuntaje() == o2.getPuntaje()) ? 0 : -1;
                    }
                });

                setRankingToView(ranking);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setRankingToView(List<Quiz> ranking){

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewRanking.setLayoutManager(manager);
        recyclerViewRanking.setAdapter(adapterQuizRanking);
        adapterQuizRanking.setRanking(ranking);


    }

    /**
     *
     * Toma un screenshot, lo guarda en el telefono y devuelve el path
     */

    public byte[] takeScreenshot(View view){



        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);


        return baos.toByteArray();
    }





}
