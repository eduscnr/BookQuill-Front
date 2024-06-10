package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelInicio;

public class FactoryInicio implements ViewModelProvider.Factory {
    private String token;

    public FactoryInicio(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelInicio.class)){
            return (T) new ViewModelInicio(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
