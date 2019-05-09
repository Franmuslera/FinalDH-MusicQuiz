package com.ejercicios.fdegregorio.musicapp.model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ejercicios.fdegregorio.musicapp.model.POJO.Album;

import java.util.List;

@Dao
public interface AlbumDBDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAlbums(List<Album> artists);

    @Query("SELECT * FROM album WHERE idArtista = :idArtist")
    List<Album> getAlbumsByArtistId(Integer idArtist);

    @Query("SELECT * FROM album WHERE id = :idAlbum")
    Album getAlbumById(Integer idAlbum);
}
