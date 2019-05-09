package com.ejercicios.fdegregorio.musicapp.model.POJO;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "track")
public class Track implements Serializable {

    @NonNull
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "nombre")
    @SerializedName("title_short")
    private String nombre;

    @ColumnInfo(name = "idArtista")
    private Integer idArtista;

    @ColumnInfo(name = "idAlbum")
    private Integer idAlbum;

    @ColumnInfo(name = "top")
    private Boolean top;

    @ColumnInfo(name = "nombreAlbum")
    private String nombreAlbum;

    @ColumnInfo(name = "nombreArtist")
    private String nombreArtist;

    @Ignore
    @SerializedName("artist")
    private Artist artist;

    @Ignore
    @SerializedName("duration")
    private Integer duracion;

    @Ignore
    @SerializedName("track_position")
    private Integer posicion;

    @Ignore
    private String preview;

    @Ignore
    private Album album;

    public Track() {
    }

    public String getNombreArtist() {
        return nombreArtist;
    }

    public void setNombreArtist(String nombreArtist) {
        this.nombreArtist = nombreArtist;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Integer idArtista) {
        this.idArtista = idArtista;
    }

    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Artist getArtist() {
        return artist;
    }

    public Album getAlbum() {
        return album;
    }

    public String getPreview() {
        return preview;
    }

    @Override
    public boolean equals(Object obj) {

        Track otroTrack = (Track) obj;

        //Si tienen el mismo id o el mismo nombre (distinto album)
        return (this.id.equals(otroTrack.getId()) ||
                (this.nombre.trim().toLowerCase()).equals((otroTrack.getNombre().trim().toLowerCase())));
    }
}
