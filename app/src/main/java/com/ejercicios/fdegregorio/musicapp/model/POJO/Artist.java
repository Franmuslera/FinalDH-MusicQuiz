package com.ejercicios.fdegregorio.musicapp.model.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "artist")
public class Artist implements Serializable {

    @NonNull
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "nombre")
    @SerializedName("name")
    private String nombre;

    @ColumnInfo(name = "imagen")
    @SerializedName("picture_big")
    private String imagen;

    @Ignore
    @SerializedName("tracklist")
    private String tracks;

    @ColumnInfo(name = "fans")
    @SerializedName("nb_fan")
    private String fans;

    public Artist() {
    }

    //El id del genero solo lo uso para Room
    @ColumnInfo(name = "idGenero")
    private Integer idGenero;

    public String getNombre() {
        return nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTracks() {
        return tracks;
    }

    public String getFans() {
        return fans;
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

    public void setTracks(String tracks) {
        this.tracks = tracks;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }
}
