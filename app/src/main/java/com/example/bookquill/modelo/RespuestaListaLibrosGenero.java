package com.example.bookquill.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaListaLibrosGenero {
    @SerializedName("librosGeneros")
    private List<Libro> libros;
    private int total;

    public RespuestaListaLibrosGenero(List<Libro> libros, int total) {
        this.libros = libros;
        this.total = total;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
