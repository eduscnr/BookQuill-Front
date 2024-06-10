package com.example.bookquill.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bookquill.MainActivity;
import com.example.bookquill.R;
import com.example.bookquill.adaptadores.AdaptadorLibrosInicio;
import com.example.bookquill.adaptadores.AdaptadorListarLibros;
import com.example.bookquill.comparadores.ComparadorLibros;
import com.example.bookquill.databinding.FragmentoListaUsuarioLibrosBinding;
import com.example.bookquill.modelo.Libro;
import com.example.bookquill.viewModel.SharedViewModel;
import com.example.bookquill.viewModel.ViewModelUsuario;
import com.example.bookquill.viewModel.viewModelFactory.FactoryUsuario;
import com.google.gson.Gson;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class FragmentoListaUsuarioLibros extends Fragment implements AdaptadorListarLibros.OnClickLibro {
    private FragmentoListaUsuarioLibrosBinding binding;
    private SharedViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentoListaUsuarioLibrosBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getArgumentos().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tituloFragmento.setText(s);
                if(s.contains("favorito")){
                    cargarFavoritos();
                }else if(s.contains("pendiente")){
                    cargarPendiente();
                }else{
                    cargarLeido();
                }
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.perfil);
            }
        });
        return binding.getRoot();
    }

    private void cargarLeido() {
        FactoryUsuario factoryUsuario = new FactoryUsuario(MainActivity.getToken());
        ViewModelUsuario viewModelUsuario = new ViewModelProvider(this, factoryUsuario).get(ViewModelUsuario.class);
        AdaptadorListarLibros adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        Disposable disposable = viewModelUsuario.flowableLibrosLeido.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
            }
        });
    }

    private void cargarPendiente() {
        FactoryUsuario factoryUsuario = new FactoryUsuario(MainActivity.getToken());
        ViewModelUsuario viewModelUsuario = new ViewModelProvider(this, factoryUsuario).get(ViewModelUsuario.class);
        AdaptadorListarLibros adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        Disposable disposable = viewModelUsuario.flowableLibrosPendientes.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
            }
        });

    }

    private void cargarFavoritos() {
        FactoryUsuario factoryUsuario = new FactoryUsuario(MainActivity.getToken());
        ViewModelUsuario viewModelUsuario = new ViewModelProvider(this, factoryUsuario).get(ViewModelUsuario.class);
        AdaptadorListarLibros adaptadorListarLibros = new AdaptadorListarLibros(new ComparadorLibros(), this);
        binding.recyclerViewListaLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewListaLibros.setAdapter(adaptadorListarLibros);
        Disposable disposable = viewModelUsuario.flowableLibrosFavoritos.subscribe(new Consumer<PagingData<Libro>>() {
            @Override
            public void accept(PagingData<Libro> libroPagingData) throws Throwable {
                adaptadorListarLibros.submitData(getLifecycle(), libroPagingData);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.perfil);
            }
        });
    }
    @Override
    public void mostrarInformacionLibro(Libro l) {
        Gson gson = new Gson();
        String libroJson = gson.toJson(l);
        Intent i = new Intent(requireContext(), ActividadMasInformacion.class);
        i.putExtra("libro", libroJson);
        startActivity(i);
    }
}