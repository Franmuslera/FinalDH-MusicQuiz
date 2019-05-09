package com.ejercicios.fdegregorio.musicapp.model.DAO;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.model.POJO.TrackContainer;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackDAO extends AppRetrofit{

    private static final String BASE_URL = "https://api.deezer.com/";
    private TrackService trackService;

    public TrackDAO() {
        super(BASE_URL);
        trackService = retrofit.create(TrackService.class);
    }

    public void getTracksSearch(String query, final ResultListener<List<Track>> listenerController) {

        Call<TrackContainer> call = trackService.getTracksSearch(query);
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
}
