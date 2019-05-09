package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;
import com.ejercicios.fdegregorio.musicapp.model.POJO.AlbumContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AlbumService {

    @GET("search/album?")
    public Call<AlbumContainer> getAlbumsByQuery(@Query("q") String query);

    @GET("album/{id}")
    public Call<Album> getAlbumById(@Path("id") Integer idAlbum);

    @GET("album/{id}/tracks")
    public Call<TrackContainer> getTracksAlbumById(@Path ("id") Integer idAlbum);

}
