package com.ejercicios.fdegregorio.musicapp.model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Artist;

import java.util.List;

@Dao
public interface ArtistDBDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertArtistAndGenre(List<Artist> artists);

    @Query("SELECT * FROM artist WHERE idGenero = :idGenero")
    List<Artist> getArtistByGenreId(Integer idGenero);

    @Query("UPDATE artist SET fans = :fans WHERE id = :idArtista")
    void updateArtistFans(Integer idArtista, String fans);

    @Query("SELECT * FROM artist WHERE id = :idArtista")
    Artist getArtistById(Integer idArtista);



}