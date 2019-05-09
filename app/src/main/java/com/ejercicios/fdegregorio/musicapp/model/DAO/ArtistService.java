package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.AlbumContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;
import com.ejercicios.fdegregorio.musicapp.model.POJO.ArtistContainer;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArtistService {

    @GET("genre/{id}/artists")
    public Call<ArtistContainer> getArtistsByGenre(@Path("id") Integer idGenero);

    @GET("search/artist?")
    public Call<ArtistContainer> getArtistByQuery(@Query("q") String query);

    @GET("artist/{id}/albums")
    public Call<AlbumContainer> getAlbumArtistById(@Path("id") Integer idArtist);

    @GET("artist/{id}/top")
    public Call<TrackContainer> getTopTracksArtistById(@Path("id") Integer idArtist);

    @GET("artist/{id}/top?limit=50")
    public Call<TrackContainer> getTopTracksByArtistId(@Path("id") Integer idArtist);

    @GET("artist/{id}")
    public Call<Artist> getArtistById(@Path("id") Integer idArtist);


}
