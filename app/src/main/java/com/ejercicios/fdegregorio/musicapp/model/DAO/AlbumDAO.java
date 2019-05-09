package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.AlbumContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDAO extends AppRetrofit{

    private static final String BASE_URL = "https://api.deezer.com/";
    private AlbumService albumService;

    public AlbumDAO() {
        super(BASE_URL);
        albumService = retrofit.create(AlbumService.class);
    }

    public void getAlbumsByQuery(String query, final ResultListener<List<Album>> listenerController){

        Call<AlbumContainer> call = albumService.getAlbumsByQuery(query);
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


    public void getAlbumById(Integer idAlbum, final ResultListener<Album> listenerController){
        Call<Album> call = albumService.getAlbumById(idAlbum);
        call.enqueue(new Callback<Album>() {
            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {
                listenerController.finish(response.body());
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        });
    }


    public void getTracksAlbumById (Integer idAlbum, final ResultListener<List<Track>> listenerController){
        Call<TrackContainer> call = albumService.getTracksAlbumById(idAlbum);
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
  /*
        call.enqueue(new Callback<AlbumContainer>() {

            @Override
            public void onResponse(Call<Album> call, Response<Album> response) {

                listenerController.finish(response.body().getData());
            }

            @Override
            public void onFailure(Call<Album> call, Throwable t) {

            }
        }); */

}
