package com.example.bookquill.modelo;

public class Genero {
    private Integer idGenero;
    private String tipo;

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Genero{" +
                "idGenero=" + idGenero +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
