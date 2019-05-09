package com.ejercicios.fdegregorio.musicapp.model.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "album")
public class Album implements Serializable {

    @NonNull
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "nombre")
    @SerializedName("title")
    private String nombre;

    @ColumnInfo(name = "imagen")
    @SerializedName("cover_medium")
    private String imagen;

    @ColumnInfo(name = "idArtista")
    private Integer idArtista;

    @Ignore
    @SerializedName("nb_tracks")
    private Integer nroTracks;

    @Ignore
    @SerializedName("genre_id")
    private Integer idGenero;

    @Ignore
    @SerializedName("release_date")
    private String anio;

    @Ignore
    @SerializedName("artist")
    private Artist artist;

    public Album() {
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAnio(){
        return anio;
    }

    public Integer getId(){ return id; }

    public Integer getNroTracks() {
        return nroTracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }
}
