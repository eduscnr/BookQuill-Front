package com.example.bookquill.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> argumentos = new MutableLiveData<>();

    public void setArgumentos(String value) {
        argumentos.setValue(value);
    }

    public LiveData<String> getArgumentos() {
        return argumentos;
    }
}
