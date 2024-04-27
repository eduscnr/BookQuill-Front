package com.example.bookquill.modelo;

import java.util.Date;

public class DetalleResenia {
    private String nombreUsuario;
    private String descripcionResenia;
    private Date fechaResenia;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDescripcionResenia() {
        return descripcionResenia;
    }

    public void setDescripcionResenia(String descripcionResenia) {
        this.descripcionResenia = descripcionResenia;
    }

    public Date getFechaResenia() {
        return fechaResenia;
    }

    public void setFechaResenia(Date fechaResenia) {
        this.fechaResenia = fechaResenia;
    }
}
