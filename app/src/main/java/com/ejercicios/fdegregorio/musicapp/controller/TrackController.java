package com.ejercicios.fdegregorio.musicapp.controller;

import com.ejercicios.fdegregorio.musicapp.model.DAO.TrackDAO;
import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;
import com.ejercicios.fdegregorio.musicapp.utils.ResultListener;

import java.util.List;

public class TrackController {

    public void getTracksSearch(String query, final ResultListener<List<Track>> listenerView){

        TrackDAO trackDAO = new TrackDAO();

        trackDAO.getTracksSearch(query, new ResultListener<List<Track>>() {
            @Override
            public void finish(List<Track> result) {
                listenerView.finish(result);
            }
        });
    }
}
