package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelLibrosGenero;

public class FactoryLibrosGenero implements ViewModelProvider.Factory{
    private String token;
    private String tipo;
    private String prueba;

    public FactoryLibrosGenero(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelLibrosGenero.class)){
            return (T) new ViewModelLibrosGenero(token,tipo);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
