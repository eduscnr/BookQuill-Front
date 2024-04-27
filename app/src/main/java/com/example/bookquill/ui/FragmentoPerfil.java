package com.example.bookquill.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.databinding.FragmentFragmentoPerfilBinding;

public class FragmentoPerfil extends Fragment {

    private FragmentFragmentoPerfilBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFragmentoPerfilBinding.inflate(inflater);
        return binding.getRoot();
    }
    public void init(){
    }
}