package com.ejercicios.fdegregorio.musicapp.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ejercicios.fdegregorio.musicapp.model.DAO.ArtistDAO;
import com.ejercicios.fdegregorio.musicapp.model.DAO.ArtistDBDAO;
import com.ejercicios.fdegregorio.musicapp.model.DAO.MusicQuizDatabase;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.ArtistContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

public class ArtistController {


    public void getArtistsByGenre(final Integer idGenre, final ResultListener<List<Artist>> listenerView, final Context context){

        ArtistDAO artistDAO = new ArtistDAO();

        if (ConnectivityController.isConnected(context)) {

            artistDAO.getArtistsByGenre(idGenre, new ResultListener<List<Artist>>() {
                @Override
                public void finish(List<Artist> result) {

                    //Actualizo el campo idGenero
                    for (Artist artist : result) {
                        artist.setIdGenero(idGenre);
                    }

                    try {
                        //Inserto los datos en la BBDD
                        MusicQuizDatabase.getDBINSTANCE(context).artistDBDAO().insertArtistAndGenre(result);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(context, "Ocurrió un problema al persistir los datos para el modo offline", Toast.LENGTH_SHORT).show();
                    }

                    listenerView.finish(result);
                }
            });
        }else {
            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).artistDBDAO().getArtistByGenreId(idGenre));
        }
    }

    public void getArtistByQuery(String query, final ResultListener<List<Artist>> listenerView){

        ArtistDAO artistDAO = new ArtistDAO();

        artistDAO.getArtistByQuery(query, new ResultListener<List<Artist>>() {
            @Override
            public void finish(List<Artist> result) {
                listenerView.finish(result);
            }
        });
    }

    public void getArtistById(final Integer idArtist, final ResultListener<Artist> listenerView, final Context context){

        ArtistDAO artistDAO = new ArtistDAO();

        if (ConnectivityController.isConnected(context)) {
            artistDAO.getArtistById(idArtist, new ResultListener<Artist>() {
                @Override
                public void finish(Artist result) {

                    //Actualizo el atributo fans
                    try {
                        //Inserto los datos en la BBDD
                        MusicQuizDatabase.getDBINSTANCE(context).artistDBDAO().updateArtistFans(idArtist, result.getFans());
                    } catch (Exception ex) {
                        Toast.makeText(context, "Ocurrió un problema al persistir los datos para el modo offline", Toast.LENGTH_SHORT).show();
                    }

                    listenerView.finish(result);
                }
            });
        }else {

            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).artistDBDAO().getArtistById(idArtist));

        }
    }
    public void getTracksArtistById(Integer idArtist, final ResultListener<List<Track>> listenerView, final Context context){

        ArtistDAO artistDAO = new ArtistDAO();

        if (ConnectivityController.isConnected(context)) {
            artistDAO.getTracksArtistById(idArtist, new ResultListener<List<Track>>() {
                @Override
                public void finish(List<Track> result) {

                    //Actualizo los idArtista y idAlbum
                    for (Track track : result) {
                        track.setIdArtista(track.getArtist().getId());
                        track.setIdAlbum(track.getAlbum().getId());
                        track.setNombreAlbum(track.getAlbum().getNombre());
                        track.setNombreArtist(track.getArtist().getNombre());
                        track.setTop(true);
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
            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).trackDBDAO().getTracksByIdArtist(idArtist));
        }
    }

    public void getAlbumArtistById(final Integer idArtist, final ResultListener<List<Album>>listenerView, final Context context){

        ArtistDAO artistDAO = new ArtistDAO();

        if (ConnectivityController.isConnected(context)) {
            artistDAO.getAlbumArtistById(idArtist, new ResultListener<List<Album>>() {
                @Override
                public void finish(List<Album> result) {

                    for (Album album : result) {
                        album.setIdArtista(idArtist);
                    }

                    try {
                        //Inserto los datos en la BBDD
                        MusicQuizDatabase.getDBINSTANCE(context).albumDBDAO().insertAlbums(result);
                    } catch (Exception ex) {
                        Toast.makeText(context, "Ocurrió un problema al persistir los datos para el modo offline", Toast.LENGTH_SHORT).show();
                    }

                    listenerView.finish(result);
                }
            });
        }else {

            listenerView.finish(MusicQuizDatabase.getDBINSTANCE(context).albumDBDAO().getAlbumsByArtistId(idArtist));
        }
    }

    public void getTopTracksByArtistId(Integer idArtist, final ResultListener<List<Track>> listenerView){

        ArtistDAO artistDAO = new ArtistDAO();
        artistDAO.getTopTracksByArtistId(idArtist, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {
                listenerView.finish(result);
            }
        });
    }
}


