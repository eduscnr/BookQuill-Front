package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelMasInformacion;

public class FactoryMasInformacion implements ViewModelProvider.Factory{
    private String token;
    private int idLibro;

    public FactoryMasInformacion(String token, int idLibro) {
        this.token = token;
        this.idLibro = idLibro;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelMasInformacion.class)){
            return (T) new ViewModelMasInformacion(token, idLibro);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
