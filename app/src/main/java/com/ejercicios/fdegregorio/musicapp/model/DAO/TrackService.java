package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrackService {

    @GET("search/track?")
    public Call<TrackContainer> getTracksSearch(@Query("q") String query);

    @GET("track/{id}")
    public Call<Track> getTracksById(@Path("id") Integer idTrack);
}
