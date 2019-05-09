package com.ejercicios.fdegregorio.musicapp.model.POJO;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Genero {

    private Integer id;

    @SerializedName("name")
    private String nombre;

    @SerializedName("picture_big")
    private String imagen;

    public Genero(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() { return imagen; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return Objects.equals(id, genero.id) &&
                Objects.equals(nombre, genero.nombre);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, nombre);
    }
}
