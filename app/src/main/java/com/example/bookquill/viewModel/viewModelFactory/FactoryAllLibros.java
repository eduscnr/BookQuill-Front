package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelAllLibros;

public class FactoryAllLibros implements ViewModelProvider.Factory {
    private String token;

    public FactoryAllLibros(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelAllLibros.class)){
            return (T) new ViewModelAllLibros(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
