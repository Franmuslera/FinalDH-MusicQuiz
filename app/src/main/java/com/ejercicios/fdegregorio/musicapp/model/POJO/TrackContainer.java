package com.ejercicios.fdegregorio.musicapp.model.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackContainer implements Serializable {

    private List<Track> data;

    public TrackContainer() {
    }

    public TrackContainer(List<Track> lst) {

        data = lst;
    }

    public List<Track> getData() {
        return data;
    }


}
