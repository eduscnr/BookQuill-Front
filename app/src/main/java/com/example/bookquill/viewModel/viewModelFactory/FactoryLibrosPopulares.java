package com.example.bookquill.viewModel.viewModelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookquill.viewModel.ViewModelLibrosPopulares;

public class FactoryLibrosPopulares implements ViewModelProvider.Factory{
    private String token;

    public FactoryLibrosPopulares(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ViewModelLibrosPopulares.class)){
            return (T) new ViewModelLibrosPopulares(token);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
