package com.ejercicios.fdegregorio.musicapp.controller;

import android.content.Context;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.model.DAO.AlbumDAO;
import com.ejercicios.fdegregorio.musicapp.model.DAO.MusicQuizDatabase;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

public class AlbumController {

    public void getAlbumsByQuery(String query, final ResultListener<List<Album>> listenerView){

        AlbumDAO albumDAO = new AlbumDAO();

        albumDAO.getAlbumsByQuery(query, new ResultListener<List<Album>>() {
            @Override
            public void finish(List<Album> result) {
                listenerView.finish(result);
            }
        });
    }


    public void getAlbumById(Integer idAlbum, final ResultListener<Album> listenerView, Context context){
        AlbumDAO albumDAO = new AlbumDAO();

        if (ConnectivityController.isConnected(context)) {
            albumDAO.getAlbumById(idAlbum, new ResultListener<Album>() {
                @Override
                public void finish(Album result) {
                    listenerView.finish(result);
                }
            });

        }else {
            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).albumDBDAO().getAlbumById(idAlbum));
        }
    }
    public void getTracksAlbumById (final Integer idAlbum, final ResultListener<List<Track>> listenerView, final Context context){

        AlbumDAO albumDAO = new AlbumDAO();

        if (ConnectivityController.isConnected(context)) {
            albumDAO.getTracksAlbumById(idAlbum, new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> result) {

                    //Actualizo los idArtista y idAlbum
                    for (Track track : result) {
                        track.setIdArtista(track.getArtist().getId());
                        track.setIdAlbum(idAlbum);
                        track.setNombreArtist(track.getArtist().getNombre());
                        track.setTop(false);
                    }

                    try {
                        //Inserto los datos en la BBDD
                        MusicQuizDatabase.getDBINSTANCE(context).trackDBDAO().insertTracksOfArtist(result);
                    } catch (Exception ex) {
                        Toast.makeText(context, "Ocurrió un problema al persistir los datos para el modo offline", Toast.LENGTH_SHORT).show();
                    }

                    listenerView.finish(result);
                }
            });

        }else {
            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).trackDBDAO().getTracksByIdAlbum(idAlbum));
        }
    }
}
