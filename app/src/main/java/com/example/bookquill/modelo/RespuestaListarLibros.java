package com.example.bookquill.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RespuestaListarLibros {
    @SerializedName("listLibrosPopulares")
    private List<Libro> libros;
    private int total;

    public RespuestaListarLibros(List<Libro> libros, int total) {
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

    @Override
    public String toString() {
        return "RespuestaListarLibros{" +
                "libros=" + libros +
                ", total=" + total +
                '}';
    }
}
