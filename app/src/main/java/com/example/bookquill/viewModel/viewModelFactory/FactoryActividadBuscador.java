package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelActividadBuscador;
import com.example.bookquill.viewModel.ViewModelInicio;

public class FactoryActividadBuscador implements ViewModelProvider.Factory {
    private String token;
    private String filtro;

    public FactoryActividadBuscador(String token, String filtro) {
        this.token = token;
        this.filtro = filtro;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelActividadBuscador.class)){
            return (T) new ViewModelActividadBuscador(token, filtro);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
