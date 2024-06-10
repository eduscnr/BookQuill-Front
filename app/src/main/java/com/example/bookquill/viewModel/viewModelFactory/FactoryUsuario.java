package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelInicio;
import com.example.bookquill.viewModel.ViewModelUsuario;

public class FactoryUsuario implements ViewModelProvider.Factory{
    private String token;

    public FactoryUsuario(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelUsuario.class)){
            return (T) new ViewModelUsuario(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
