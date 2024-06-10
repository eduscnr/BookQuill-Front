package com.example.bookquill.modelo;

public class ReseniaDTO {
    private int idUsuario;
    private String texto;
    private int idLibro;

    public ReseniaDTO(int idUsuario, String texto, int idLibro) {
        this.idUsuario = idUsuario;
        this.texto = texto;
        this.idLibro = idLibro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
}