package com.ejercicios.fdegregorio.musicapp.model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Track;

import java.util.List;

@Dao
public interface TrackDBDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTracksOfArtist(List<Track> tracks);

    @Query("SELECT * FROM track WHERE idArtista = :idArtista AND top = 1")
    List<Track> getTracksByIdArtist(Integer idArtista);

    @Query("SELECT * FROM track WHERE idAlbum = :idAlbum")
    List<Track> getTracksByIdAlbum(Integer idAlbum);

}
