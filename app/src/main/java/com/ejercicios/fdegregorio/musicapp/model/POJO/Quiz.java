package com.ejercicios.fdegregorio.musicapp.model.POJO;

import android.support.annotation.NonNull;

import java.util.List;

public class Quiz {

    private Integer puntaje;
    private String fecha;
    private String nombreUsuario;



    public Quiz() {
    }



    public Quiz(Integer puntaje, String fecha, String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }


    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
