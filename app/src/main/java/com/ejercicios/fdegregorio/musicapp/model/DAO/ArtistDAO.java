package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.AlbumContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.ArtistContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArtistDAO extends AppRetrofit {

    private static final String BASE_URL = "https://api.deezer.com/";
    private ArtistService artistService;

    public ArtistDAO() {
        super(BASE_URL);
        artistService = retrofit.create(ArtistService.class);
    }

    public void getArtistsByGenre(Integer idGenre, final ResultListener<List<Artist>> listenerController){

        Call<ArtistContainer> call = artistService.getArtistsByGenre(idGenre);
        call.enqueue(new Callback<ArtistContainer>() {
            @Override
            public void onResponse(Call<ArtistContainer> call, Response<ArtistContainer> response) {

                listenerController.finish(response.body().getData());
            }

            @Override
            public void onFailure(Call<ArtistContainer> call, Throwable t) {

            }
        });
    }

    public void getArtistByQuery(String query, final ResultListener<List<Artist>> listenerController){

        Call<ArtistContainer> call = artistService.getArtistByQuery(query);
        call.enqueue(new Callback<ArtistContainer>() {
            @Override
            public void onResponse(Call<ArtistContainer> call, Response<ArtistContainer> response) {

                listenerController.finish(response.body().getData());
            }

            @Override
            public void onFailure(Call<ArtistContainer> call, Throwable t) {

            }
        });
    }
    public void getArtistById(Integer idArtist, final ResultListener<Artist> listenerController){
        Call<Artist> call = artistService.getArtistById(idArtist);
        call.enqueue(new Callback<Artist>() {
            @Override
            public void onResponse(Call<Artist> call, Response<Artist> response) {
                listenerController.finish(response.body());
            }

            @Override
            public void onFailure(Call<Artist> call, Throwable t) {

            }
        });
    }

    public void getTopTracksByArtistId(Integer idArtist, final ResultListener<List<Track>> listenerController){
        Call<TrackContainer> call = artistService.getTopTracksByArtistId(idArtist);
        call.enqueue(new Callback<TrackContainer>() {
            @Override
            public void onResponse(Call<TrackContainer> call, Response<TrackContainer> response) {
                listenerController.finish(response.body().getData());
            }
            @Override
            public void onFailure(Call<TrackContainer> call, Throwable t) {

            }
        });
    }

    public void getTracksArtistById(Integer idArtist, final ResultListener<List<Track>> listenerController){
        Call<TrackContainer> call = artistService.getTopTracksArtistById(idArtist);
        call.enqueue(new Callback<TrackContainer>() {
            @Override
            public void onResponse(Call<TrackContainer> call, Response<TrackContainer> response) {
                listenerController.finish(response.body().getData());
            }

            @Override
            public void onFailure(Call<TrackContainer> call, Throwable t) {

            }
        });
    }
    public void getAlbumArtistById(Integer idArtist, final ResultListener<List<Album>>listenerController){
        Call<AlbumContainer> call = artistService.getAlbumArtistById(idArtist);
        call.enqueue(new Callback<AlbumContainer>() {
            @Override
            public void onResponse(Call<AlbumContainer> call, Response<AlbumContainer> response) {
                listenerController.finish(response.body().getData());
            }

            @Override
            public void onFailure(Call<AlbumContainer> call, Throwable t) {

            }
        });

    }

}



