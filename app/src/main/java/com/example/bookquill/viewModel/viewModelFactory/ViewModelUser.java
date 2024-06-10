package com.example.bookquill.viewModel.viewModelFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookquill.modelo.UsuarioDTO;

public class ViewModelUser extends ViewModel {
    private MutableLiveData<UsuarioDTO> mutableLiveData = new MutableLiveData<>();
    public LiveData<UsuarioDTO> usuarioDTO = mutableLiveData;

    public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
        mutableLiveData.setValue(usuarioDTO);
    }
}
